# ビルドステージ（Mavenがインストールされているイメージを使用）
FROM maven:3.9.5-eclipse-temurin-17 AS builder  # ★この行を変更★
WORKDIR /app
COPY . .
# Mavenを実行して、アプリケーションをJARファイルにパッケージ化し、名前を app.jar に変更する
RUN mvn clean package && mv target/*.jar target/app.jar

# 実行ステージ（軽量なJRE環境でJARを実行する）
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# ビルドステージからJARファイルをコピー
COPY --from=builder /app/target/app.jar /app/app.jar
# アプリケーションの起動
ENTRYPOINT ["java", "-jar", "app.jar"]