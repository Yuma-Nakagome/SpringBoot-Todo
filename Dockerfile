# --- ステージ 1: ビルドステージ ---
# Java 17 JDKを持つイメージをベースにする
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY . .
# Mavenを実行して、アプリケーションをJARファイルにパッケージ化し、名前を app.jar に変更する
RUN mvn clean package && mv target/*.jar target/app.jar

# --- ステージ 2: 実行ステージ ---
# 軽量なJREイメージをベースにする
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# ビルドステージで作成したJARファイル（app.jar）をコピー
COPY --from=builder /app/target/app.jar /app/app.jar
# アプリケーションが待ち受けるポートを公開（Spring Bootのデフォルトは8080）
EXPOSE 8080
# アプリケーションの起動コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]