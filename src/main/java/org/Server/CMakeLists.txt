cmake_minimum_required(VERSION 3.22)
project(Server C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(Server
        constants.h
        GameData.h
        server.h
        GameData.c
        server.c)
