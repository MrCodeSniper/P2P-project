

cmake_minimum_required(VERSION 3.4.1)


add_library(

             native-lib
             # TODO STATIC表示静态的.a的库，SHARED表示.so的库
             SHARED
             src/main/cpp/native-lib.cpp

                )




find_library(
              log-lib
              log )


target_link_libraries(
                       native-lib
                       ${log-lib} )

include_directories(src/main/cpp/bzip2)