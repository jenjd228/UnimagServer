package com.example.demo.Service;

import com.example.demo.DTO.Order2ProductDTO;
import com.example.demo.DTO.Order2ProductInterface;
import com.example.demo.DTO.OrdersDTO;
import com.example.demo.DTO.PayDTO;
import com.example.demo.Model.Order2Product;
import com.example.demo.Model.Orders;
import com.example.demo.Model.User;
import com.example.demo.Repository.Order2productRepository;
import com.example.demo.Repository.OrdersRepository;
import com.example.demo.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Service
public class OrderService {

    @Qualifier("modelMapperToOrderDTO")
    private final ModelMapper modelMapperToOrderDTO;

    @Qualifier("modelMapperToOrderToProductDTO")
    private final ModelMapper modelMapperToOrderToProductDTO;

    private final OrdersRepository ordersRepository;

    private final UserRepository userRepository;

    private final Order2productRepository order2ProductRepository;

    OrderService(ModelMapper modelMapperToOrderToProductDTO,ModelMapper modelMapperToOrderDTO,Order2productRepository order2ProductRepository, OrdersRepository ordersRepository, UserRepository userRepository) {
        this.modelMapperToOrderToProductDTO = modelMapperToOrderToProductDTO;
        this.modelMapperToOrderDTO = modelMapperToOrderDTO;
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.order2ProductRepository = order2ProductRepository;
    }



    public void addToOrder(String secureKod, String productList, String orderId, String totalMoney, String pickUpPoint) {
        User user = userRepository.findBySecureKod(secureKod);
        List<Order2Product> order2ProductList = new ArrayList<>();

        List<PayDTO> participantJsonList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            participantJsonList = objectMapper.readValue(productList, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        if (user != null) {
            //Integer bonus = Bonus.countBonus(Integer.parseInt(totalMoney));
            //user.addPoints(bonus);

            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //String text = LocalDateTime.now().format(formatter);

            Long currentDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();

            Orders order = new Orders();
            order.setDataOfOrder(currentDate);
            order.setOrderId(Integer.parseInt(orderId));
            order.setStatus("Не доставлено");
            order.setPickUpPoint(pickUpPoint);
            order.setUserId(user.getId());

            for (PayDTO product : participantJsonList) {
                Order2Product order2Product = new Order2Product();
                order2Product.setOrderId(Integer.parseInt(orderId));
                order2Product.setProductId(product.getProductId());
                order2Product.setCount(product.getCount());
                order2Product.setSize(product.getSize());

                order2ProductList.add(order2Product);
            }

            if (order2ProductList.size() != 0) { //Может быть ошибка Duplicate entry '1790251900' for key 'orders.UK_hmsk25beh6atojvle1xuymjj0'
                ordersRepository.save(order);
                order2ProductRepository.saveAll(order2ProductList);
            }
        }
    }

    public Object[] getOrdersList(String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);

        //System.out.println(Arrays.toString(order2productRepository.findBy().toArray()));
        if (user != null) {
            return Objects.requireNonNull(user.getOrdersList()).stream().map(this::convertToOrdersDto).toArray();
        }
        return new Object[0];
    }

    private OrdersDTO convertToOrdersDto(Orders order) {
        return Objects.isNull(order) ? null : modelMapperToOrderDTO.map(order, OrdersDTO.class);
    }

    public List<Order2ProductDTO> getProductListByOrderId(String secureKod,Integer orderId){
        User user = userRepository.findBySecureKod(secureKod);

        List<Order2ProductDTO> productDTOS;

        if (user == null){
            return null;
        }else {
            productDTOS = order2ProductRepository.findProductBySecureKodAndOrderId(user.getId(),orderId).stream().map(this::convertToOrderToProductDto).collect(toList());
            return productDTOS;
        }
    }

    private Order2ProductDTO convertToOrderToProductDto(Order2ProductInterface order) {
        return Objects.isNull(order) ? null : modelMapperToOrderToProductDTO.map(order, Order2ProductDTO.class);
    }
}
