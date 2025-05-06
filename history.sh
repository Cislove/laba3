#!/bin/bash

set -e  # Прервать при ошибке
set -o pipefail
echo "Running shell: $SHELL"

# Настройки
BASE_DIR=$(pwd)
TMP_DIR="$BASE_DIR/tmp-history"
OUT_DIR="$BASE_DIR/history-out"
ZIP_NAME="team-builds.zip"
REPO_URL=$(git config --get remote.origin.url)

# Очистка старых данных
rm -rf "$TMP_DIR" "$OUT_DIR" "$ZIP_NAME"
mkdir -p "$TMP_DIR" "$OUT_DIR"

# Получаем 2 предыдущих коммита (без HEAD)
COMMITS=($(git rev-list --max-count=3 HEAD))
COMMITS=("${COMMITS[@]:1:2}") # исключаем HEAD, берём 2 предыдущих

echo "Коммиты для сборки:"
printf " - %s\n" "${COMMITS[@]}"

# Для каждого коммита
for COMMIT in "${COMMITS[@]}"; do
  WORK_DIR="$TMP_DIR/$COMMIT"
  echo "→ Клонируем $COMMIT в $WORK_DIR"
  git clone "$REPO_URL" "$WORK_DIR"
  cd "$WORK_DIR"
  git checkout "$COMMIT"

  echo "→ Собираем $COMMIT"
  mvn clean install -Pbuild

  echo "→ Копируем JAR-файлы"
  find "$WORK_DIR/target" -name "*.jar" -exec cp {} "$OUT_DIR/${COMMIT:0:7}.jar" \;

  cd "$BASE_DIR"
done

# Архивируем
echo "→ Упаковка в $ZIP_NAME"
zip -j "$ZIP_NAME" "$OUT_DIR"/*.jar

echo "✔ Готово. Архив: $ZIP_NAME"