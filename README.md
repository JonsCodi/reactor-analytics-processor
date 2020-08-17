# reactor-analytics-processor
API Reactive about txt processor

Serviço reativo e Watcher de diretório para processamento em lote. 

# How to run

## Pre-requirements
 - Docker
 - Java 11
 - Gradle 6+
 
#### Running with Gradle
```shell
$ ./gradlew bootRun
```

#### Running with Docker

```shell
$ gradle clean build
$ docker build -t app-img .
$ docker run -v ${USER_HOME}/data:/root/data -p 8080:8080 --name app-run app-img
```

## How to execute manual test

### Copia e Cola
Tu pode copiar o arquivo no formato txt com este conteúdo:
```txt
001ç1234567891234çPedroç50000
001ç3245678865434çPauloç40000.99
002ç2345675434544345çJose da SilvaçRural
002ç2345675433444345çEduardo PereiraçRural
003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro
003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo
```
e colar no diretório `${USER_HOME}/data/in` e o serviço começará a processar as informações 
e irá gerar o resultado no diretório `${USER_HOME}/data/out`. 

### Usando o endpoint reativo.

 --- 
  **POST** /files
 
 ````shell script
$ curl --location --request POST 'http://localhost:8080/files' 
--form 'files=@registro01.txt' --form 'files=@registro02.txt'
````

 Response:
 ```json
{"status":"Successfully uploaded"}
 ```

E o resultado vai para `${USER_HOME}/data/out`.
 

### To do
---
* Unit Test
* Integration test
* Exception Handler
* Docs