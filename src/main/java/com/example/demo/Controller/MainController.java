package com.example.demo.Controller;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.DTO.OrdersDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Email.SendEmail;
import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@RestController
public class MainController {

    @Autowired
    private SendEmail sendEmail;

    @Autowired
    private EmailKodRepository emailKodRepository;

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private BasketRepository basketRepository;

    /*@PostMapping("sendMessage")
    public ResponseEntity sendMessage(@RequestParam String email) {
        //@RequestParam String message, @RequestParam String mail
        String kod = String.valueOf(new Random().nextInt(9999));
        EmailKod emailKod = new EmailKod();
        emailKod.setEmail(email);
        emailKod.setKod(kod);
        User user = userRepository.findByEmail(email);
        if (user==null){
            sendEmail.sendEmail(kod,email);
            emailKodRepository.save(emailKod);
            return new ResponseEntity("no",HttpStatus.OK);
        }
        return new ResponseEntity("yes",HttpStatus.OK);
    }*/

    @PostMapping("firstUpdate")
    public ResponseEntity firstUpdate(@RequestParam String email,@RequestParam String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        User userFromBD = userRepository.findByEmail(email);
        if (userFromBD!=null){
            return new ResponseEntity("yes",HttpStatus.OK);
        }
        Calendar currentDate = Calendar.getInstance();
        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.add(Calendar.DAY_OF_MONTH,7);

        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        String hex = javax.xml.bind.DatatypeConverter.printHexBinary(salt.digest());

        User user = new User();
        user.setPoints(0);
        user.setEmail(email);
        user.setPassword(password);
        user.setRegistrationDate(currentDate.getTime());
        user.setMaxDate(calendarFrom.getTime());
        user.setSecureKod(hex);

        userRepository.save(user);
        return new ResponseEntity(hex,HttpStatus.OK);
    }


    @GetMapping("checkBySecureKod/{secureKod}")
    public ResponseEntity checkBySecureKod(@PathVariable String secureKod) {
        //@RequestParam String message, @RequestParam String mail
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            return new ResponseEntity("ok",HttpStatus.OK);
        }
        return new ResponseEntity("NOT_FOUND",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("userUpdate")
    public ResponseEntity userUpdate(@RequestParam String email,@RequestParam String fio,@RequestParam String birthData) {
        User user = userRepository.findByEmail(email);
        if (user!=null){
            user.setFio(fio);
            user.setBirthday(birthData);
            userRepository.save(user);
            return new ResponseEntity("ok",HttpStatus.OK);
        }
        return new ResponseEntity("Error!",HttpStatus.OK);

    }

    @GetMapping("getList/{currentNumberList}")
    public ResponseEntity getList(@PathVariable Integer currentNumberList){
        Long productCount = catalogRepository.count();
        PageRequest pageable = PageRequest.of(currentNumberList,8,Sort.Direction.ASC,"date");
        if (productCount==0){
            return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
        }
        int countGroup = (int) ((productCount/8)+1);//например 100/8 = 12.5 значит всего 13 групп
        if (currentNumberList<=countGroup){
            List<Catalog> list = catalogRepository.findBy(pageable);
            return new ResponseEntity(list,HttpStatus.OK);
        }
        return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("getUser/{secureKod}")
    public ResponseEntity getUser(@PathVariable String secureKod){
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(user,HttpStatus.OK);
        }
    }

    @PostMapping("addToBasket")
    public ResponseEntity addToBasket(@RequestParam String id,@RequestParam String secureKod) { // не правильно прописана логика
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            List<BasketProduct> basketProduct1 = basketRepository.findByUserId(user.getId());
            if (basketProduct1.size()!=0){
                for (BasketProduct basketProduct:basketProduct1){
                    if (basketProduct.getId().toString().equals(id)){
                        basketProduct.setCount(basketProduct.getCount()+1);
                        basketRepository.save(basketProduct);
                        break;
                    }
                }
                return new ResponseEntity("ok",HttpStatus.OK);
            }
            Optional<Catalog> product1 = catalogRepository.findById(Integer.valueOf(id));
            Catalog product = null;
            if (product1.isPresent()){
                 product = product1.get();
            }
            if (product!=null){
                    BasketProduct basketProduct = new BasketProduct();
                    basketProduct.setUserId(user.getId());
                    basketProduct.setProductId(product.getId());
                    basketProduct.setCount(1);
                    basketRepository.save(basketProduct);
                return new ResponseEntity("ok",HttpStatus.OK);
            }
            return new ResponseEntity("PRODUCT_NOT_FOUND",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("USER_NOT_FOUND",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("addToOrders")
    public ResponseEntity addToOrders(@RequestParam String stringIds,@RequestParam String secureKod) throws JsonProcessingException {
        List<OrdersDTO> list = new ArrayList<>();
        Calendar currentDate = Calendar.getInstance();
        List<String> list1 = Arrays.asList(stringIds.split(","));
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity(HttpStatus.OK);
        }
        Orders order;
        for (String id:list1){
            order = new Orders();
            order.setProductId(Integer.valueOf(id));
            order.setDataOfOrder(currentDate.getTime());
            order.setUserId(user.getId());
            order.setStatus("Не доставлено");
            ordersRepository.save(order);
        }
        return new ResponseEntity("ok",HttpStatus.OK);

    }

    @GetMapping("getBasketList/{secureKod}")
    public ResponseEntity getBasketList(@PathVariable String secureKod){
        //Long productCount = basketRepository.count();
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity("BAD_REQUEST",HttpStatus.BAD_REQUEST);
        }
        Integer id = user.getId();
        List<BasketProduct> list = basketRepository.findByUserId(id);
        if (list.size()!=0){
            List<Integer> ids = new ArrayList<>();
            for (BasketProduct basketProduct:list){
                ids.add(basketProduct.getProductId());
            }
            List<Catalog> list2 = catalogRepository.findByUserIds(ids);
            List<BasketProductDTO> list3 = new ArrayList<>();
            for (Catalog catalog:list2){
                BasketProductDTO basketProductDTO = new BasketProductDTO(); //переделать с помощью join возможно
                basketProductDTO.setProductId(catalog.getId());
                basketProductDTO.setImageName(catalog.getImageName());
                basketProductDTO.setCategory(catalog.getCategory());
                basketProductDTO.setPrice(catalog.getPrice());
                basketProductDTO.setDate(catalog.getDate());
                basketProductDTO.setDescriptions(catalog.getDescriptions());
                basketProductDTO.setTitle(catalog.getTitle());
                for (BasketProduct basketProduct:list){
                    if(basketProduct.getProductId().equals(catalog.getId())){
                        basketProductDTO.setCount(basketProduct.getCount());
                        break;
                    }
                }
                list3.add(basketProductDTO);
            }
            return new ResponseEntity(list3,HttpStatus.OK);
        }
        return new ResponseEntity("Error!",HttpStatus.NOT_FOUND);
    }

    @GetMapping("deleteBasketProduct/{secureKod}/{productId}")
    public ResponseEntity deleteBasketProduct(@PathVariable String secureKod,@PathVariable Integer productId){
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        List<BasketProduct> list = basketRepository.findByUserId(user.getId());
        if (list!=null){
            for (BasketProduct basketProduct:list){
                if (basketProduct.getProductId()==productId){
                    basketRepository.deleteById(basketProduct.getId());
                    break;
                }
            }
            return new ResponseEntity("ok",HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("qwe")
    public void qwer(){
        User user = userRepository.findByEmail("ekocaba2@mail.ru");
        user.setPoints(165);
        userRepository.save(user);
    }



    @GetMapping("addList")
    public ResponseEntity addList(){
            Catalog catalog = new Catalog();
            catalog.setCategory("Одежда");
            catalog.setDate(new Date());
            catalog.setDescriptions("Свитшот утепленный");
            catalog.setPrice(1600);
            catalog.setTitle("Свитшот");
            catalog.setImageName("1.jpg");
            catalogRepository.save(catalog);

        Catalog catalog1 = new Catalog();
        catalog1.setCategory("Одежда");
        catalog1.setDate(new Date());
        catalog1.setDescriptions("Толстовка (худи), утепленное, цвет синий.");
        catalog1.setPrice(1700);
        catalog1.setTitle("Худи утепленное");
        catalog1.setImageName("q.jpg");
        catalogRepository.save(catalog1);

        Catalog catalog2 = new Catalog();
        catalog2.setCategory("Одежда");
        catalog2.setDate(new Date());
        catalog2.setDescriptions("Толстовка женская. Материал: 92%-хлопок, 8%-лайкра");
        catalog2.setPrice(1500);
        catalog2.setTitle("Толстовка женская");
        catalog2.setImageName("6.jpg");
        catalogRepository.save(catalog2);

        Catalog catalog4 = new Catalog();
        catalog4.setCategory("Одежда");
        catalog4.setDate(new Date());
        catalog4.setDescriptions("Толстовка женская на молнии. Материал: 92%-хлопок, 8%-лайкра");
        catalog4.setPrice(1500);
        catalog4.setTitle("Толстовка женская");
        catalog4.setImageName("4.jpg");
        catalogRepository.save(catalog4);

        Catalog catalog5 = new Catalog();
        catalog5.setCategory("Сувенирная продукция");
        catalog5.setDate(new Date());
        catalog5.setDescriptions("Часы изготовлены из массива ясеня");
        catalog5.setPrice(1200);
        catalog5.setTitle("Часы ЮФУ ясень");
        catalog5.setImageName("29.jpg");
        catalogRepository.save(catalog5);

        Catalog catalog9 = new Catalog();
        catalog9.setCategory("Сувенирная продукция");
        catalog9.setDate(new Date());
        catalog9.setDescriptions("Кружка керамическая");
        catalog9.setPrice(500);
        catalog9.setTitle("Кружка керамическая");
        catalog9.setImageName("18.jpg");
        catalogRepository.save(catalog9);

        Catalog catalog6 = new Catalog();
        catalog6.setCategory("Сувенирная продукция");
        catalog6.setDate(new Date());
        catalog6.setDescriptions("Футболка");
        catalog6.setPrice(1200);
        catalog6.setTitle("Футболка Юфу");
        catalog6.setImageName("5.jpg");
        catalogRepository.save(catalog6);

        Catalog catalog7 = new Catalog();
        catalog7.setCategory("Сувенирная продукция");
        catalog7.setDate(new Date());
        catalog7.setDescriptions("Флешка из массива ясеня");
        catalog7.setPrice(900);
        catalog7.setTitle("Флешка ЮФУ ясень");
        catalog7.setImageName("16.jpg");
        catalogRepository.save(catalog7);

        Catalog catalog8 = new Catalog();
        catalog8.setCategory("Сувенирная продукция");
        catalog8.setDate(new Date());
        catalog8.setDescriptions("Блокнот");
        catalog8.setPrice(300);
        catalog8.setTitle("Блокнот Юфу");
        catalog8.setImageName("27.jpg");
        catalogRepository.save(catalog8);

        Catalog catalog0 = new Catalog();
        catalog0.setCategory("Сувенирная продукция");
        catalog0.setDate(new Date());
        catalog0.setDescriptions("Худи");
        catalog0.setPrice(1200);
        catalog0.setTitle("Черное худи");
        catalog0.setImageName("7.jpg");
        catalogRepository.save(catalog0);

        Catalog catalog22 = new Catalog();
        catalog22.setCategory("Сувенирная продукция");
        catalog22.setDate(new Date());
        catalog22.setDescriptions("Комплект ручек");
        catalog22.setPrice(1000);
        catalog22.setTitle("Комплект из 2-х ручек");
        catalog22.setImageName("22.jpg");
        catalogRepository.save(catalog22);

        return new ResponseEntity(HttpStatus.OK);
    }


}
