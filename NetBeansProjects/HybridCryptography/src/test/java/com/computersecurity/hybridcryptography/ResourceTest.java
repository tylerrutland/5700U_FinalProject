/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography;

import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class ResourceTest {

    private final String path = "src/test/resources/";

    @Test
    public void testMainView() {
        File file = new File(path + "fxml/MainView.fxml");
        boolean expected = true;
        boolean result = file.exists();
        assertEquals(expected, result);
    }

    @Test
    public void testImage() {
        File file = new File(path + "images/palmTree.bmp");
        boolean expected = true;
        boolean result = file.exists();
        assertEquals(expected, result);
    }

}
