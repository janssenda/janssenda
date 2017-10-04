/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.morefunctionaltesting;

import static com.dm.morefunctionaltesting.main.trimOne;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author danimaetrix
 */
public class mainTest {

    @Test
    public void testTrimOne() {

        // trimOne("Hello") -> "ell"
        // trimOne("java") -> "av"
        // trimOne("coding") -> "odin"
        
        assertEquals(trimOne("Hello"),"ell");
        
        
    }

}
