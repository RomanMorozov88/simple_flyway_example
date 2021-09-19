# simple_flyway_example
Spring Boot + FlyWay + H2 + JDBCTemplate + Gradle  
Json-object example  
POST on /packs/savePack  
```  
{
    "name": "test pack",
    "blocks": [
        {
            "className" : "TextBlock",
            "name" : "tb01",
            "text" : "text in tb01"
        },
        {
            "className" : "LocalDateBlock",
            "name" : "ldb01",
            "firstDate" : "2021-05-05",
            "secondDate" : "2022-06-06"
        }
    ]
}
```  

