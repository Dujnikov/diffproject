cmake_minimum_required(VERSION 3.5)

project(testopensslfasad LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(OpenSSL REQUIRED)
include_directories(${OPENSSL_INCLUDE_DIR})

add_executable(testopensslfasad main.cpp ../common/opensslx509tools.cpp ../common/opensslx509tools.h)

target_link_libraries(testopensslfasad ${OPENSSL_LIBRARIES})
