#!/bin/sh
echo "[INFO] 使用Maven运行单元测试及集成测试用例."
echo "[INFO] 请确保Derby数据库启动."

cd ..
mvn integration-test