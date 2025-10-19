package com.example.practicetsibin.data

class MockMovieRepository : MovieRepository {
    val movies: List<Movie> = listOf(
        Movie(
            id = "1",
            title = "Начало",
            year = 2010,
            posterUrl = "",
            rating = 8.8,
            genres = listOf("Боевик", "Фантастика", "Триллер"),
            description = "Профессионал проникает в сны, чтобы внедрить идею в сознание человека."
        ),
        Movie(
            id = "2",
            title = "Интерстеллар",
            year = 2014,
            posterUrl = "",
            rating = 8.6,
            genres = listOf("Приключения", "Драма", "Фантастика"),
            description = "Команда исследователей отправляется через червоточину на поиски нового дома для человечества."
        ),
        Movie(
            id = "3",
            title = "Тёмный рыцарь",
            year = 2008,
            posterUrl = "",
            rating = 9.0,
            genres = listOf("Боевик", "Криминал", "Драма"),
            description = "Бэтмен сталкивается с хаотичным Джокером, ставящим Готэм на грань анархии."
        ),
        Movie(
            id = "4",
            title = "Дюна",
            year = 2021,
            posterUrl = "",
            rating = 8.1,
            genres = listOf("Фантастика", "Драма", "Приключения"),
            description = "Наследник великого дома отправляется на пустынную планету Арракис."
        ),
        Movie(
            id = "5",
            title = "Матрица",
            year = 1999,
            posterUrl = "",
            rating = 8.7,
            genres = listOf("Фантастика", "Боевик"),
            description = "Хакер Нео узнаёт, что мир — это симуляция, и присоединяется к сопротивлению."
        ),
        Movie(
            id = "6",
            title = "Паразиты",
            year = 2019,
            posterUrl = "",
            rating = 8.5,
            genres = listOf("Драма", "Триллер"),
            description = "Бедная семья внедряется в дом богатых, что приводит к неожиданным последствиям."
        ),
        Movie(
            id = "7",
            title = "Зелёная миля",
            year = 1999,
            posterUrl = "",
            rating = 9.1,
            genres = listOf("Драма", "Фэнтези"),
            description = "Тюремный надзиратель знакомится с необычным заключённым, обладающим даром исцеления."
        ),
        Movie(
            id = "8",
            title = "Побег из Шоушенка",
            year = 1994,
            posterUrl = "",
            rating = 9.3,
            genres = listOf("Драма"),
            description = "Банкир, несправедливо осуждённый, планирует побег и не теряет надежды."
        ),
        Movie(
            id = "9",
            title = "Аватар",
            year = 2009,
            posterUrl = "",
            rating = 7.8,
            genres = listOf("Фантастика", "Приключения"),
            description = "Бывший морпех отправляется на планету Пандора и становится частью народа На'ви."
        ),
        Movie(
            id = "10",
            title = "Властелин колец: Братство кольца",
            year = 2001,
            posterUrl = "",
            rating = 8.8,
            genres = listOf("Фэнтези", "Приключения"),
            description = "Хоббит Фродо начинает опасное путешествие, чтобы уничтожить Кольцо Всевластия."
        ),
        Movie(
            id = "11",
            title = "Гарри Поттер и философский камень",
            year = 2001,
            posterUrl = "",
            rating = 7.6,
            genres = listOf("Фэнтези", "Приключения"),
            description = "Мальчик-волшебник поступает в Хогвартс и узнаёт о своём предназначении."
        ),
        Movie(
            id = "12",
            title = "Джентльмены",
            year = 2019,
            posterUrl = "",
            rating = 7.8,
            genres = listOf("Криминал", "Комедия"),
            description = "Наркобарон пытается продать бизнес, но попадает в водоворот интриг."
        ),
        Movie(
            id = "13",
            title = "Форрест Гамп",
            year = 1994,
            posterUrl = "",
            rating = 8.9,
            genres = listOf("Драма", "Романтика"),
            description = "История простодушного Форреста, невольно оказавшегося участником важных событий."
        ),
        Movie(
            id = "14",
            title = "1+1",
            year = 2011,
            posterUrl = "",
            rating = 8.8,
            genres = listOf("Драма", "Комедия"),
            description = "Дружба аристократа-инвалида и молодого помощника меняет их жизни."
        ),
        Movie(
            id = "15",
            title = "Бойцовский клуб",
            year = 1999,
            posterUrl = "",
            rating = 8.7,
            genres = listOf("Драма", "Триллер"),
            description = "Клерк знакомится с Тайлером Дёрденом и погружается в мир подпольного клуба."
        )
    )

    override fun getAllMovies(): List<Movie> = movies
    
    override fun getMovieById(id: String): Movie? = movies.find { it.id == id }
}


