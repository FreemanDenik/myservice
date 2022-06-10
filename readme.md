<h2>Gradle, билд и запуск проекта</h2>
<p>Используем Gradle Wrapper</p>
<p>Доступ к фазам Gradle у нас будет через gradlew</p>
<ul>
         <li><p><b>.\gradlew wrapper --gradle-version 7.4.2</b>: обновить gradle до версии 7.4.2 </p>  </li>
        <li><p><b>.\gradlew build</b>: билд проекта</p></li>
        <li><p><b>.\gradlew jar</b>: билд jar архива </p></li>
        <li><p><b>.\gradlew bootRun</b>: запустить проект из Gradle</p></li>
        <li>
            <p>запустить jar архив из командной строки</p>
            <ul>
                <li><p><b>.\gradlew jar</b>: архивация проекта</p></li>
                <li>в командной строке перейти в папку <b>[папка проекта]/build/libs/</b></li>
                <li>вызвать команду выполнения jar <b>java -jar myservice-0.0.1-SNAPSHOT.jar</b></li>
            </ul>
        </li>
       
</ul>

<h2>Конечные точки</h2>
<b>По умолчанию сравнение с USD (доллар)</b><br>
<b>Изменить можно в конфиг файле application.properties: my.xchange.base-code</b>

<p>Все валюты сравниваются с своим вчерашним значением по отношению к USD т.е. доллару</p>
<ul>
    <li>
        <p>Получить список валют допустимых к сравнению</p>
        <a href="http://localhost:8080/actuator/currencies">http://localhost:8080/actuator/currencies</a>
    </li>
    <li>
        <p>Сравнить валюты и получить gif контент в соответствии rich, broke</p>
        <p>В адресной строке, что ниже сравниваем доллар с биткоином т.е. BTC</p>
        <a href="http://localhost:8080/actuator/compare/BTC">http://localhost:8080/actuator/compare/BTC</a>
  </li>
</ul>


<h2>Уровни рейтинга gif контента</h2>
<b>По умолчанию рейтинг PG</b><br>
<b>Изменить можно в конфиг файле application.properties: my.giphy.rating</b>

<ul>
    <li><span>G</span> : Содержит изображения, которые широко признаны уместными и обычно видны людям в общественных местах.</li>
    <li><span>PG</span> : Содержит изображения, которые обычно видны в общедоступной среде, но не так широко распространены, как это уместно.</li>
    <li><span>PG-13</span> : Содержит изображения, которые обычно не видны, если их не искать, но которые все же часто можно увидеть.</li>
    <li><span>R</span> : Содержит изображения, которые обычно не видны, если их не найти, и могут быть сочтены тревожными, если их засвидетельствовать.</li>
</ul>