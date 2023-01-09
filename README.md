Amostra de aplicativo Liveness (Android) 
===============================

Este é um exemplo de aplicativo Android que usa a tecnologia Liveness da Clearsale
para de prova de vida (também conhecida como Liveness).

##  Construindo o aplicativo de exemplo

Primeiro, clone o repositório:

`git clone https://github.com/ClearSale/LivenessSampleAndroid.git`

Construir a amostra depende de suas ferramentas de construção.

###  Android Studio (recomendado)

(Estas instruções foram testadas com o Android Studio versão 2.2.2, 2.2.3, 2.3 e 2.3.2)

* Abra o Android Studio e selecione `File->Open...`
* Selecione o diretório do aplicativo de exemplo
* Clique em 'OK' para abrir o projeto no Android Studio.
* Uma sincronização do Gradle deve começar, mas você pode forçar uma sincronização e criar o módulo 'aplicativo' conforme necessário.

###  Gradle (linha de comando)

* Construa o APK: `./gradlew build`


##  Executando o aplicativo de exemplo

*Conecte um dispositivo Android à sua máquina de desenvolvimento.
* Coloque suas credenciais no arquivo `settings.gradle`
* Coloque a versão do SDK Liveness de sua preferencia nas dependencias do arquivo `build.gradle` 
* Coloque o `clientSecret` e `clientId` na linha 34 da MainActivity.java
* Sincronize e Build o projeto

Obs: O nosso SDK não roda em emuladores, apenas em dispositivos fisícos

###  Android Studio

* Selecione `Run -> Run app'` (ou `Debug 'app'` ) na barra de menus
* Selecione o dispositivo no qual deseja executar o aplicativo e clique em 'OK'

###  Gradle

* Instale o APK de depuração no seu dispositivo `./gradlew installDebug`
* Inicie o APK: `<path to Android SDK>/platform-tools/adb -d shell am start br.clearsale.studio.sampleliveness/br.clearsale.studio.sampleliveness.MainActivity`


##  Usando o aplicativo de amostra

Ao pressionar o botão "Start CS Liveness" o SDK Liveness iniciará, após completar o fluxo o aplicativo retornara o Resultado, Imagem e SessionId em caso de sucesso ou um erro
