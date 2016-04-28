/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Sets the number of threads that the application is going to utilize
 */
public class AppExecutorService {

    public static final ExecutorService pool
            = Executors.newSingleThreadExecutor();

}
