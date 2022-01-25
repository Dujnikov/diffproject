cmake_minimum_required(VERSION 3.14)

include_directories (/usr/lib/jvm/bellsoft-java8.x86_64/include)
include_directories (/usr/lib/jvm/bellsoft-java8.x86_64/include/linux)

project(opensslfasad LANGUAGES CXX)

set(CMAKE_INCLUDE_CURRENT_DIR ON)
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

find_package(OpenSSL REQUIRED)

add_library(opensslfasad SHARED
  opensslfasad.cpp
  ./common/opensslx509tools.cpp
)

target_link_libraries(opensslfasad ${OPENSSL_LIBRARIES})

target_compile_definitions(opensslfasad PRIVATE OPENSSLFASAD_LIBRARY)
