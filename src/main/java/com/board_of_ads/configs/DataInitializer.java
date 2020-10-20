package com.board_of_ads.configs;

import com.board_of_ads.models.Category;
import com.board_of_ads.models.Image;
import com.board_of_ads.models.Notification;
import com.board_of_ads.models.Role;
import com.board_of_ads.models.User;
import com.board_of_ads.models.posting.Posting;
import com.board_of_ads.service.interfaces.CategoryService;
import com.board_of_ads.service.interfaces.CityService;
import com.board_of_ads.service.interfaces.KladrService;
import com.board_of_ads.service.interfaces.NotificationService;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.RoleService;
import com.board_of_ads.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;
    private final KladrService kladrService;
    private final CategoryService categoryService;
    private final PostingService postingService;
    private final CityService cityService;
    private final NotificationService notificationService;

    @PostConstruct
    private void init() throws IOException {
        initUsers();
        initKladr();
        initCategories();
        initPosting();
        initNotifications();
    }

    private void initUsers() {

        if (roleService.getRoleByName("ADMIN") == null) {
            roleService.saveRole(new Role("ADMIN"));
        }
        if (roleService.getRoleByName("USER") == null) {
            roleService.saveRole(new Role("USER"));
        }
        if (userService.getUserByEmail("admin@mail.ru") == null) {
            User admin = new User();
            admin.setEmail("admin@mail.ru");
            admin.setPassword("1234567");
            admin.setFirsName("Admin");
            admin.setLastName("Admin");
            admin.setAvatar(new Image(null, "images/admin.jpg"));
            Set<Role> roleAdmin = new HashSet<>();
            roleAdmin.add(roleService.getRoleByName("ADMIN"));
            admin.setRoles(roleAdmin);
            userService.saveUser(admin);
        }

        if (userService.getUserByEmail("user@mail.ru") == null) {
            User user = new User();
            user.setEmail("user@mail.ru");
            user.setPassword("1234567");
            user.setFirsName("User");
            user.setLastName("User");
            user.setAvatar(new Image(null, "images/user.jpg"));
            Set<Role> roleAdmin = new HashSet<>();
            roleAdmin.add(roleService.getRoleByName("USER"));
            user.setRoles(roleAdmin);
            userService.saveUser(user);
        }
    }

    private void initCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Транспорт", null, 1));
        categoryList.add(new Category("Недвижимость", null, 1));
        categoryList.add(new Category("Работа", null, 1));
        categoryList.add(new Category("Услуги", null, 1));
        categoryList.add(new Category("Личные вещи", null, 1));
        categoryList.add(new Category("Для дома и дачи", null, 1));
        categoryList.add(new Category("Бытовая электроника", null, 1));
        categoryList.add(new Category("Хобби и отдых", null, 1));
        categoryList.add(new Category("Животные", null, 1));
        categoryList.add(new Category("Для бизнеса", null, 1));

        for (Category category : categoryList) {
            if (categoryService.getCategoryByName(category.getName()).isEmpty()) {
                categoryService.saveCategory(category);
            }
        }


        List<Category> subCategoryList = new ArrayList<>();
        subCategoryList.add(new Category("Автомобили", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Мотоциклы и мототехника", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Грузовики и спецтранспорт", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Водный транспорт", categoryService.getCategoryByName("Транспорт").get(), 2));
        subCategoryList.add(new Category("Запчасти и автоаксессуары", categoryService.getCategoryByName("Транспорт").get(), 2));

        subCategoryList.add(new Category("Квартиры", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Комнаты", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Дома, дачи, коттеджи", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Гаражи и машиноместа", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Земельные участки", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Коммерческая недвижимость", categoryService.getCategoryByName("Недвижимость").get(), 2));
        subCategoryList.add(new Category("Недвижимость за рубежом", categoryService.getCategoryByName("Недвижимость").get(), 2));

        subCategoryList.add(new Category("Вакансии", categoryService.getCategoryByName("Работа").get(), 2));
        subCategoryList.add(new Category("Резюме", categoryService.getCategoryByName("Работа").get(), 2));

        subCategoryList.add(new Category("Одежда, обувь, аксессуары", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Детская одежда и обувь", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Товары для детей и игрушки", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Часы и украшения", categoryService.getCategoryByName("Личные вещи").get(), 2));
        subCategoryList.add(new Category("Красота и здоровье", categoryService.getCategoryByName("Личные вещи").get(), 2));

        subCategoryList.add(new Category("Бытовая техника", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Мебель и интерьер", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Посуда и товары для кухни", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Продукты питания", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Ремонт и строительство", categoryService.getCategoryByName("Для дома и дачи").get(), 2));
        subCategoryList.add(new Category("Растения", categoryService.getCategoryByName("Для дома и дачи").get(), 2));

        subCategoryList.add(new Category("Аудио и видео", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Игры, приставки и программы", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Настольные компьютеры", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Ноутбуки", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Оргтехника и расходники", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Планшеты и электронные книги", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Телефоны", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Товары для компьютера", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Фототехника", categoryService.getCategoryByName("Бытовая электроника").get(), 2));
        subCategoryList.add(new Category("Билеты и путешествия", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Велосипеды", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Книги и журналы", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Коллекционирование", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Музыкальные инструменты", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Охота и рыбалка", categoryService.getCategoryByName("Хобби и отдых").get(), 2));
        subCategoryList.add(new Category("Спорт и отдых", categoryService.getCategoryByName("Хобби и отдых").get(), 2));

        subCategoryList.add(new Category("Собаки", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Кошки", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Птицы", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Аквариум", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Другие животные", categoryService.getCategoryByName("Животные").get(), 2));
        subCategoryList.add(new Category("Товары для животных", categoryService.getCategoryByName("Животные").get(), 2));

        subCategoryList.add(new Category("Готовый бизнес", categoryService.getCategoryByName("Для бизнеса").get(), 2));
        subCategoryList.add(new Category("Оборудование для бизнеса", categoryService.getCategoryByName("Для бизнеса").get(), 2));

        for (Category category : subCategoryList) {
            if (categoryService.getCategoryByName(category.getName()).isEmpty()) {
                categoryService.saveCategory(category);
            }
        }



        List<Category> secondSubCategory = new ArrayList<>();
        secondSubCategory.add(new Category("С пробегом", categoryService.getCategoryByName("Транспорт:Автомобили").get(), 3));
        secondSubCategory.add(new Category("Новые", categoryService.getCategoryByName("Транспорт:Автомобили").get(), 3));

        secondSubCategory.add(new Category("Багги", categoryService.getCategoryByName("Транспорт:Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Вездеходы", categoryService.getCategoryByName("Транспорт:Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Картинг", categoryService.getCategoryByName("Транспорт:Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Квадроциклы", categoryService.getCategoryByName("Транспорт:Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Мопеды и скутеры", categoryService.getCategoryByName("Транспорт:Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Мотоциклы", categoryService.getCategoryByName("Транспорт:Мотоциклы и мототехника").get(), 3));
        secondSubCategory.add(new Category("Снегоходы", categoryService.getCategoryByName("Транспорт:Мотоциклы и мототехника").get(), 3));

        secondSubCategory.add(new Category("Автобусы", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Автодома", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Автокраны", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Бульдозеры", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Грузовики", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Коммунальная техника", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Легкий транспорт", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Погрузчики", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Прицепы", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Сельхозтехникам", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Строительная техника", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Техника для лесозаготовки", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Тягачи", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));
        secondSubCategory.add(new Category("Эскаваторы", categoryService.getCategoryByName("Транспорт:Грузовики и спецтранспорт").get(), 3));

        secondSubCategory.add(new Category("Вёсельные лодки", categoryService.getCategoryByName("Транспорт:Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Гидроциклы", categoryService.getCategoryByName("Транспорт:Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Катера и яхты", categoryService.getCategoryByName("Транспорт:Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Каяки и каноэ", categoryService.getCategoryByName("Транспорт:Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Моторные лодки", categoryService.getCategoryByName("Транспорт:Водный транспорт").get(), 3));
        secondSubCategory.add(new Category("Надувные лодки", categoryService.getCategoryByName("Транспорт:Водный транспорт").get(), 3));

        secondSubCategory.add(new Category("Запчасти", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("GPS-навигаторы", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Автокосметика и автохимия", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Аудио и видеотехника", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Багажники и фаркопы", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Инструменты", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Прицепы", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Противоугонные устройства", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Тюнинг", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Шины, диски и колеса", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));
        secondSubCategory.add(new Category("Экипировка", categoryService.getCategoryByName("Транспорт:Запчасти и автоаксессуары").get(), 3));


        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость:Квартиры").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость:Квартиры").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость:Квартиры").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость:Квартиры").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость:Комнаты").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость:Комнаты").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость:Комнаты").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость:Комнаты").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость:Дома, дачи, коттеджи").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость:Дома, дачи, коттеджи").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость:Дома, дачи, коттеджи").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость:Дома, дачи, коттеджи").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость:Гаражи и машиноместа").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость:Гаражи и машиноместа").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость:Гаражи и машиноместа").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость:Гаражи и машиноместа").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость:Земельные участки").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость:Земельные участки").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость:Земельные участки").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость:Земельные участки").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость:Коммерческая недвижимость").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость:Коммерческая недвижимость").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость:Коммерческая недвижимость").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость:Коммерческая недвижимость").get(), 3));

        secondSubCategory.add(new Category("Продам", categoryService.getCategoryByName("Недвижимость:Недвижимость за рубежом").get(), 3));
        secondSubCategory.add(new Category("Сдам", categoryService.getCategoryByName("Недвижимость:Недвижимость за рубежом").get(), 3));
        secondSubCategory.add(new Category("Куплю", categoryService.getCategoryByName("Недвижимость:Недвижимость за рубежом").get(), 3));
        secondSubCategory.add(new Category("Сниму", categoryService.getCategoryByName("Недвижимость:Недвижимость за рубежом").get(), 3));


        secondSubCategory.add(new Category("IT, интернет, телеком", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Автомобильный бизнес", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Административная работа", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Банки, инвестиции", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Без опыта, студенты", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Бухгалтерия, финансы", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Высший менеджмент", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Госслужба, НКО", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Домашний персонал", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("ЖКХ, эксплуатация", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Исскуство, развлечения", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Консультирование", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Маркетинг, реклама, PR", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Медицина, фармацевтика", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Образование, наука", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Охрана, безопасность", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Продажи", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Производство, сырьё, с/х", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Страхование", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Строительство", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Транспорт, логистика", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Туризм, рестораны", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Управление персоналом", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Фитнес, салоны красоты", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));
        secondSubCategory.add(new Category("Юриспруденция", categoryService.getCategoryByName("Работа:Вакансии").get(), 3));

        secondSubCategory.add(new Category("IT, интернет, телеком", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Автомобильный бизнес", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Административная работа", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Банки, инвестиции", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Без опыта, студенты", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Бухгалтерия, финансы", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Высший менеджмент", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Госслужба, НКО", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Домашний персонал", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("ЖКХ, эксплуатация", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Исскуство, развлечения", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Консультирование", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Маркетинг, реклама, PR", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Медицина, фармацевтика", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Образование, наука", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Охрана, безопасность", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Продажи", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Производство, сырьё, с/х", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Страхование", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Строительство", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Транспорт, логистика", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Туризм, рестораны", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Управление персоналом", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Фитнес, салоны красоты", categoryService.getCategoryByName("Работа:Резюме").get(), 3));
        secondSubCategory.add(new Category("Юриспруденция", categoryService.getCategoryByName("Работа:Резюме").get(), 3));


        secondSubCategory.add(new Category("Женская одежда", categoryService.getCategoryByName("Личные вещи:Одежда, обувь, аксессуары").get(), 3));
        secondSubCategory.add(new Category("Мужская одежда", categoryService.getCategoryByName("Личные вещи:Одежда, обувь, аксессуары").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Личные вещи:Одежда, обувь, аксессуары").get(), 3));

        secondSubCategory.add(new Category("Для девочек", categoryService.getCategoryByName("Личные вещи:Детская одежда и обувь").get(), 3));
        secondSubCategory.add(new Category("Для мальчиков", categoryService.getCategoryByName("Личные вещи:Детская одежда и обувь").get(), 3));

        secondSubCategory.add(new Category("Автомобильные кресла", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Велосипеды и самокаты", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Детская мебель", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Детские коляски", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Игрушки", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Постельные принадлежности", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Товары для кормления", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Товары для купания", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));
        secondSubCategory.add(new Category("Товары для школы", categoryService.getCategoryByName("Личные вещи:Товары для детей и игрушки").get(), 3));

        secondSubCategory.add(new Category( "Бижутерия", categoryService.getCategoryByName("Личные вещи:Часы и украшения").get(), 3));
        secondSubCategory.add(new Category("Часы", categoryService.getCategoryByName("Личные вещи:Часы и украшения").get(), 3));
        secondSubCategory.add(new Category("Ювелирные изделия", categoryService.getCategoryByName("Личные вещи:Часы и украшения").get(), 3));

        secondSubCategory.add(new Category("Косметика", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Парфюмерия", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Приборы и аксессуары", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Средства гигиены", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Средства для волос", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Медицинские изделия", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Биологически активные добавки", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));
        secondSubCategory.add(new Category("Услуги", categoryService.getCategoryByName("Личные вещи:Красота и здоровье").get(), 3));


        secondSubCategory.add(new Category("Для дома", categoryService.getCategoryByName("Для дома и дачи:Бытовая техника").get(), 3));
        secondSubCategory.add(new Category("Для индивидуального ухода", categoryService.getCategoryByName("Для дома и дачи:Бытовая техника").get(), 3));
        secondSubCategory.add(new Category("Для кухни", categoryService.getCategoryByName("Для дома и дачи:Бытовая техника").get(), 3));
        secondSubCategory.add(new Category("Климатическое оборудование", categoryService.getCategoryByName("Для дома и дачи:Бытовая техника").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Для дома и дачи:Бытовая техника").get(), 3));

        secondSubCategory.add(new Category("Компьютерные столы и кресла", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Кровати, диваны и кресла", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Кухонные гарнитуры", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Освещение", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Подставки и тумбы", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Предметы интерьера, искусство", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Столы и стулья", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Текстиль и ковры", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Шкафы и комоды", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Для дома и дачи:Мебель и интерьер").get(), 3));

        secondSubCategory.add(new Category("Посуда", categoryService.getCategoryByName("Для дома и дачи:Посуда и товары для кухни").get(), 3));
        secondSubCategory.add(new Category("Товары для кухни", categoryService.getCategoryByName("Для дома и дачи:Посуда и товары для кухни").get(), 3));

        secondSubCategory.add(new Category("Двери", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Инструменты", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Камины и обогреватели", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Окна и балконы", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Потолки", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Садовая техника", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Сантехника и сауна", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Стройматериалы", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));
        secondSubCategory.add(new Category("Услуги", categoryService.getCategoryByName("Для дома и дачи:Ремонт и строительство").get(), 3));


        secondSubCategory.add(new Category("MP3 плееры", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Акустика, колонки, сабвуферы", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Видео, DVD и Blu-Ray плееры", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Видеокамеры", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Кабели и адаптеры", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Микрофоны", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Музыка и фильмы", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Музыкальные центры, магнитолы", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Наушники", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Телевизоры и проекторы", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Усилители и ресиверы", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Бытовая электроника:Аудио и видео").get(), 3));

        secondSubCategory.add(new Category("Игры для приставок", categoryService.getCategoryByName("Бытовая электроника:Игры, приставки и программы").get(), 3));
        secondSubCategory.add(new Category("Игровые приставки", categoryService.getCategoryByName("Бытовая электроника:Игры, приставки и программы").get(), 3));
        secondSubCategory.add(new Category("Компьютерные игры", categoryService.getCategoryByName("Бытовая электроника:Игры, приставки и программы").get(), 3));
        secondSubCategory.add(new Category("Программы", categoryService.getCategoryByName("Бытовая электроника:Игры, приставки и программы").get(), 3));

        secondSubCategory.add(new Category("МФУ, копиры и сканнеры", categoryService.getCategoryByName("Бытовая электроника:Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Принтеры", categoryService.getCategoryByName("Бытовая электроника:Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Телефония", categoryService.getCategoryByName("Бытовая электроника:Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("ИБП, сетевые фильтры", categoryService.getCategoryByName("Бытовая электроника:Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Уничтожители бумаг", categoryService.getCategoryByName("Бытовая электроника:Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Расходные материалы", categoryService.getCategoryByName("Бытовая электроника:Оргтехника и расходники").get(), 3));
        secondSubCategory.add(new Category("Канцелярия", categoryService.getCategoryByName("Бытовая электроника:Оргтехника и расходники").get(), 3));

        secondSubCategory.add(new Category("Планшеты", categoryService.getCategoryByName("Бытовая электроника:Планшеты и электронные книги").get(), 3));
        secondSubCategory.add(new Category("Электронные книги", categoryService.getCategoryByName("Бытовая электроника:Планшеты и электронные книги").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Бытовая электроника:Планшеты и электронные книги").get(), 3));

        secondSubCategory.add(new Category("Акустика", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Веб-камеры", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Джойстики и руль", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Клавиатуры и мыши", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Комплектующее", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Мониторы", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Переносные жёсткие диски", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Сетевое оборудование", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("ТВ-тюнеры", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Флешки и карты памяти", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));
        secondSubCategory.add(new Category("Акксессуары", categoryService.getCategoryByName("Бытовая электроника:Товары для компьютера").get(), 3));

        secondSubCategory.add(new Category("Компактные фотоаппараты", categoryService.getCategoryByName("Бытовая электроника:Фототехника").get(), 3));
        secondSubCategory.add(new Category("Зеркальные фотоаппараты", categoryService.getCategoryByName("Бытовая электроника:Фототехника").get(), 3));
        secondSubCategory.add(new Category("Пленочные фотоаппараты", categoryService.getCategoryByName("Бытовая электроника:Фототехника").get(), 3));
        secondSubCategory.add(new Category("Бинокли и телескопы", categoryService.getCategoryByName("Бытовая электроника:Фототехника").get(), 3));
        secondSubCategory.add(new Category("Объективы", categoryService.getCategoryByName("Бытовая электроника:Фототехника").get(), 3));
        secondSubCategory.add(new Category("Оборудование и аксессуары", categoryService.getCategoryByName("Бытовая электроника:Фототехника").get(), 3));


        secondSubCategory.add(new Category("Карты, купоны", categoryService.getCategoryByName("Хобби и отдых:Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Концерты", categoryService.getCategoryByName("Хобби и отдых:Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Путешествия", categoryService.getCategoryByName("Хобби и отдых:Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Спорт", categoryService.getCategoryByName("Хобби и отдых:Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Театр, опера, балет", categoryService.getCategoryByName("Хобби и отдых:Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Цирк, кино", categoryService.getCategoryByName("Хобби и отдых:Билеты и путешествия").get(), 3));
        secondSubCategory.add(new Category("Шоу, мюзикл", categoryService.getCategoryByName("Хобби и отдых:Билеты и путешествия").get(), 3));

        secondSubCategory.add(new Category("Горные", categoryService.getCategoryByName("Хобби и отдых:Велосипеды").get(), 3));
        secondSubCategory.add(new Category("Дорожные", categoryService.getCategoryByName("Хобби и отдых:Велосипеды").get(), 3));
        secondSubCategory.add(new Category("BMX", categoryService.getCategoryByName("Хобби и отдых:Велосипеды").get(), 3));
        secondSubCategory.add(new Category("Детские", categoryService.getCategoryByName("Хобби и отдых:Велосипеды").get(), 3));
        secondSubCategory.add(new Category("Запчасти и аксессуары", categoryService.getCategoryByName("Хобби и отдых:Велосипеды").get(), 3));

        secondSubCategory.add(new Category("Журналы, газеты, брошюры", categoryService.getCategoryByName("Хобби и отдых:Книги и журналы").get(), 3));
        secondSubCategory.add(new Category("Книги", categoryService.getCategoryByName("Хобби и отдых:Книги и журналы").get(), 3));
        secondSubCategory.add(new Category("Учебная литература", categoryService.getCategoryByName("Хобби и отдых:Книги и журналы").get(), 3));

        secondSubCategory.add(new Category("Банкноты", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Билеты", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Вещи знаменитостей, автографы", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Военные вещи", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Грампластинки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Документы", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Жетоны, медали, значки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Игры", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Календари", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Картины", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Киндер-Сюрприз", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Конверты и почтовые карточки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Макеты оружия", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Марки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Модели", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Монеты", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Открытки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Пепельницы, зажигалки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Пластиковые карточки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Спортивные карточки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Фотографии, письма", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Этикетки, бутылки, пробки", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Хобби и отдых:Коллекционирование").get(), 3));

        secondSubCategory.add(new Category("Аккордеоны, гармонии, баяны", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Гитары и другие струнные", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Духовные", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Пианино и другие клавишные", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Скрипки и другие смычковые", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Ударные", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Для студии и концертов", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));
        secondSubCategory.add(new Category("Аксессуары", categoryService.getCategoryByName("Хобби и отдых:Музыкальные инструменты").get(), 3));

        secondSubCategory.add(new Category("Бильярд и боулинг", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Дайвинг и водный спорт", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Единоборства", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Зимние виды спорта", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Игры с мячом", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Настольные игры", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Пейнтбол и страйкбол", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Ролики и скейтбординг", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Теннис, бадминтон, пинг-понг", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Туризм", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Финтес и тренажеры", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Спортивное питание", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Хобби и отдых:Спорт и отдых").get(), 3));

        secondSubCategory.add(new Category("Интернет-магазин", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Общественное питание", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Производство", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Развлечение", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Сельское хозяйство", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Строительство", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Сфера услуг", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Торговля", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Для бизнеса:Готовый бизнес").get(), 3));

        secondSubCategory.add(new Category("Для магазина", categoryService.getCategoryByName("Для бизнеса:Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Для офиса", categoryService.getCategoryByName("Для бизнеса:Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Для ресторана", categoryService.getCategoryByName("Для бизнеса:Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Для салона красоты", categoryService.getCategoryByName("Для бизнеса:Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Промышленное", categoryService.getCategoryByName("Для бизнеса:Оборудование для бизнеса").get(), 3));
        secondSubCategory.add(new Category("Другое", categoryService.getCategoryByName("Для бизнеса:Оборудование для бизнеса").get(), 3));

        for (Category category : secondSubCategory) {
            if (categoryService.getCategoryByName(category.getName()).isEmpty()) {
                categoryService.saveCategory(category);
            }
        }
    }

    private void initKladr() throws IOException {
        kladrService.streamKladr();
    }

    private void initPosting() {
        List<Posting> postingList = new ArrayList<>();
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Поглажу кота", "Очень качественно", 100L, "+79998887766", cityService.findCityByName("Ростов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Транспорт").get()
                , "Поддержу советом", "Не факт что полезным", 50L, "+79998887766", cityService.findCityByName("Ростов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Недвижимость").get()
                , "Ремонт электроники", "Быстро, качественно", 1000L, "+79998887766", cityService.findCityByName("Ростов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Недвижимость").get()
                , "Монтаж электросетей", "Любая сложность", 10_000L, "+79998887766", cityService.findCityByName("Азов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Транспорт").get()
                , "Няня", "от 1 года", 2_000L, "+79998887766", cityService.findCityByName("Азов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Посмотрю телевизор за Вас", "только 16к", 1_000L, "+79998887766", cityService.findCityByName("Азов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Схожу за продуктами", "Могу в Ашан, могу в Пятерочку", 1_000L, "+79998887766", cityService.findCityByName("Азов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Выгуляю собаку", "Ей понравится", 1_000L, "+79998887766", cityService.findCityByName("Азов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Газовщик", "Любая сложность", 2_000L, "+79998887766", cityService.findCityByName("Азов").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Врач", "Терапевт", 3_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Транспорт").get()
                , "Стоматолог", "Будет не больно", 5_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Недвижимость").get()
                , "Киллер", "Будет больно", 300_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Собутыльник", "Будет весело", 500L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Грузовые перевозки", "Трезвые грузчики", 10_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Сыграю в лото", "Я в этом хорош", 500L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Разобью сердце", "Только парням", 10_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Схожу в кино", "За компанию", 1_000L, "+79998887766", cityService.findCityByName("Ростов-на-Дону").get()));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Сдамся в рабство", "ненадолго", 50_000L, "+79998887766"));
        postingList.add(new Posting(userService.getUserByEmail("admin@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Уведу у Вас девушку", "Вдруг она вам надоела", 3_000L, "+79998887766"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Обижу обидчиков", "Не старше 18 лет", 5_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Отмажу от ментов", "У меня папка начальник", 10_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Вынесу мусор", "Небольшой", 400L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Научу играть на гитаре", "Учил самого Цоя", 10_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Научу играть в Warcraft", "PvP или зассал?", 10_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Продам средство для похудения", "Результат уже через 3 дня. Нужно всего лишь 1 ложка...", 10_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Продам ядерный реактор", "Самовывоз с Припяти", 500_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Стань программистом за 1 урок", "Урок №1: перезагрузка роутера", 20_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Свадебный фотограф", "Фоткаю на iPhone 7", 10_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Компьютерный мастер на дом", "Перезагружу ваш компьютер быстро и качественно", 4_000L, "+79896661488"));
        postingList.add(new Posting(userService.getUserByEmail("user@mail.ru"), categoryService.getCategoryByName("Услуги").get()
                , "Сесть на пенёк", "...Почему тут так мало?", 1_000L, "+79896661488"));

        for (Posting posting : postingList) {
            if (postingService.getPostingByTitle(posting.getTitle()).isEmpty()) {
                postingService.save(posting);
            }
        }
    }

    private void initNotifications() {
        List<User> groupOfAllUsers = userService.getAllUsers();
        for (User oneUser: groupOfAllUsers ) {
            log.info(oneUser.getUsername());
        }
        List<User> oneUser = Collections.singletonList(userService.getUserById(3L));
        Notification notification1 = new Notification("The first Notification",
                "This is a notification from Memory! How are you, Friends?", groupOfAllUsers);
        Notification notification2 = new Notification("The second Notification",
                "This is another notification from Memory. How are you, Mate?", oneUser);
        Notification notification3 = new Notification("The third Notification",
                "This is third notification from Memory. How are you, Mate?", oneUser);
        Notification notification4 = new Notification("The 4th Notification",
                "This is 4th notification from Memory. How are you, Mate?", oneUser);
        notificationService.createNotification(notification1);
        notificationService.createNotification(notification2);
        notificationService.createNotification(notification3);
        notificationService.createNotification(notification4);
//        notificationService.sendNotificationToUsers(notification1,groupOfAllUsers);
//        notificationService.updateUserNotificationsList(notification1, oneUser.get(0));
    //    System.out.println(notificationService.getNotificationById(3L));
        notificationService.getAllNotifications();
        notificationService.getUsersAllNotifications(userService.getUserById(3L));
        System.out.println("-------");
        System.out.println("Calling method get Notification by id");
        System.out.println(notificationService.getNotificationById(0L));



    }
}
