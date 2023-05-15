package com.cbozan.dao;



import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

import com.cbozan.entity.Paytype;
import com.cbozan.entity.Price;
import com.cbozan.exception.EntityException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PaytypeDAOTest {

    private PaytypeDAO paytypeDAO;
    private Connection conn = null;
    @Before
    public void setUp() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","12345678");//12345678 for zarea db
        paytypeDAO = PaytypeDAO.getInstance();
        paytypeDAO.setUsingCache(true);
    }
    @After
    public void tearDown() throws SQLException {
        paytypeDAO.list().clear();
        PreparedStatement st = conn.prepareStatement("delete from paytype;");
        st.executeUpdate();
    }
    @Test
    public void testFindById() throws EntityException {

        Paytype paytype =  new Paytype.PaytypeBuilder()
                .setId(5)
                .setDate(Timestamp.valueOf("2000-11-18 18:11:05"))
                .setTitle("Cash")
                .build();
        paytypeDAO.create(paytype);
        Paytype paytypet =paytypeDAO.list().get(0);
        assertNotNull(paytypet);
        assertEquals("Cash", paytypet.getTitle());
    }
    @Test
    public void testFindByIdNotFound() throws EntityException {
        Paytype paytype = paytypeDAO.findById(100);
        assertNull(paytype);
    }
    @Test
    public void testList() throws EntityException {
        Paytype paytype1 =  new Paytype.PaytypeBuilder()
                .setId(6)
                .setDate(Timestamp.valueOf("2000-11-18 18:11:05"))
                .setTitle("Cash")
                .build();
        paytypeDAO.create(paytype1);
        Paytype paytype2 =  new Paytype.PaytypeBuilder()
                .setId(7)
                .setDate(Timestamp.valueOf("2000-11-18 18:11:05"))
                .setTitle("visa")
                .build();
        paytypeDAO.create(paytype2);
        Paytype paytype3 =  new Paytype.PaytypeBuilder()
                .setId(8)
                .setDate(Timestamp.valueOf("2000-11-18 18:11:05"))
                .setTitle("online")
                .build();
        paytypeDAO.create(paytype3);
        List<Paytype> list = paytypeDAO.list();
        assertNotNull(list);
        assertEquals(3, list.size());
    }
    @Test
    public void testCreate() throws EntityException {
        Paytype paytype = new Paytype.PaytypeBuilder()
                .setId(9)
                .setTitle("Test Paytype")
                .build();
        boolean result = paytypeDAO.create(paytype);
        Assert.assertTrue(result);
        Paytype found = paytypeDAO.findById(paytype.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(paytype.getTitle(), found.getTitle());
    }
    @Test
    public void testCreateDuplicate() throws EntityException {
        Paytype paytype = new Paytype.PaytypeBuilder()
                .setId(10)
                .setTitle("Cash")
                .build();
        Paytype paytype2 = new Paytype.PaytypeBuilder()
                .setId(11)
                .setTitle("Cash")
                .build();
        boolean result = paytypeDAO.create(paytype);
        boolean result2 = paytypeDAO.create(paytype2);
        Assert.assertFalse(result2);
        Assert.assertEquals("Cash kaydı zaten mevcut.", DB.ERROR_MESSAGE);
    }
    @Test
    public void testUpdate() throws EntityException {
        Paytype paytype = new Paytype.PaytypeBuilder()
                .setId(12)
                .setTitle("Cash0")
                .build();
        paytypeDAO.create(paytype);

        paytype.setTitle("Updated Cash");
        boolean result = paytypeDAO.update(paytype);
        Assert.assertTrue(result);
        Paytype found = paytypeDAO.findById(paytype.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals(paytype.getTitle(), found.getTitle());
    }
    @Test
    public void testDelete() throws EntityException {
                Paytype paytype = new Paytype.PaytypeBuilder()
                .setId(15)
                .setTitle("Credit Card")
                .build();
        paytypeDAO.create(paytype);
        Paytype paytype2 = paytypeDAO.findById(15);
        boolean result = paytypeDAO.delete(paytype2);
        Assert.assertTrue(result);
        Paytype found = paytypeDAO.findById(paytype.getId());
        Assert.assertNull(found);
    }
    @Test
    public void testUpdateControl() throws EntityException {
        Paytype paytype = new Paytype.PaytypeBuilder()
                .setId(22)
                .setTitle("Credit Card")
                .build();

        Paytype paytype2 = new Paytype.PaytypeBuilder()
                .setId(23)
                .setTitle("online")
                .build();

        paytypeDAO.create(paytype);
        paytypeDAO.create(paytype2);

        paytype.setId(23);
        paytype.setTitle("online");
        assertFalse(paytypeDAO.update(paytype));
    }

}