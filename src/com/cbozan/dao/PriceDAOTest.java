package com.cbozan.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import org.junit.*;

import com.cbozan.entity.Price;
import com.cbozan.exception.EntityException;

public class PriceDAOTest {

    private PriceDAO priceDAO;
    private  Connection conn = null;


    @Before
    public  void setUp() throws Exception {
        //todo 12345 for abdullah
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","12345678");//12345678 for zarea db
        priceDAO = PriceDAO.getInstance();
    }

    @After
    public  void tearDown() throws Exception {
        priceDAO.list().clear();
        PreparedStatement st = conn.prepareStatement("delete from price;");
        st.executeUpdate();

    }

    @Test
    public void testCreate() throws EntityException {
        Price price = new Price.PriceBuilder()
                .setId(1)
                .setFulltime(BigDecimal.valueOf(99))
                .setHalftime(BigDecimal.valueOf(60))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        assertTrue(priceDAO.create(price));
    }

    @Test
    public void testFindById() throws EntityException {
        Price price = new Price.PriceBuilder()
                .setId(2)
                .setFulltime(BigDecimal.valueOf(110))
                .setHalftime(BigDecimal.valueOf(60))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        priceDAO.create(price);

        priceDAO.findById(price.getId());


        assertEquals(priceDAO.list().get(0).getHalftime(), price.getHalftime());
    }

    @Test
    public void testList() throws EntityException {
        Price price1 = new Price.PriceBuilder()
                .setId(4)
                .setFulltime(BigDecimal.valueOf(100))
                .setHalftime(BigDecimal.valueOf(70))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        Price price2 = new Price.PriceBuilder()
                .setId(5)
                .setFulltime(BigDecimal.valueOf(140))
                .setHalftime(BigDecimal.valueOf(90))
                .setOvertime(BigDecimal.valueOf(180))
                .build();

        priceDAO.create(price1);
        priceDAO.create(price2);

        List<Price> prices = priceDAO.list();

        assertEquals(2, prices.size());
        assertTrue(prices.contains(price1));
        assertTrue(prices.contains(price2));
    }

    @Test
    public void testUpdate() throws EntityException {
        Price price = new Price.PriceBuilder()
                .setId(6)
                .setFulltime(BigDecimal.valueOf(90))
                .setHalftime(BigDecimal.valueOf(60))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        priceDAO.create(price);

        price.setFulltime(BigDecimal.valueOf(190));
        price.setHalftime(BigDecimal.valueOf(110));
        price.setOvertime(BigDecimal.valueOf(240));

        assertTrue(priceDAO.update(price));

        Price updatedPrice = priceDAO.findById(price.getId());

        assertEquals(updatedPrice, price);
    }

    @Test
    public void testDelete() throws EntityException {
        Price price = new Price.PriceBuilder()
                .setId(7)
                .setFulltime(BigDecimal.valueOf(80))
                .setHalftime(BigDecimal.valueOf(60))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        priceDAO.create(price);

        assertTrue(priceDAO.delete(price));
        assertNull(priceDAO.findById(price.getId()));
    }

    @Test
    public void testCreateControl() throws EntityException {
        Price price1 = new Price.PriceBuilder()
                .setId(8)
                .setFulltime(BigDecimal.valueOf(100))
                .setHalftime(BigDecimal.valueOf(50))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        Price price2 = new Price.PriceBuilder()
                .setId(9)
                .setFulltime(BigDecimal.valueOf(100))
                .setHalftime(BigDecimal.valueOf(50))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        priceDAO.create(price1);

        assertFalse(priceDAO.create(price2));
    }

    @Test
    public void testUpdateControl() throws EntityException {
        Price price1 = new Price.PriceBuilder()
                .setId(25)
                .setFulltime(BigDecimal.valueOf(100))
                .setHalftime(BigDecimal.valueOf(60))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        Price price2 = new Price.PriceBuilder()
                .setId(58)
                .setFulltime(BigDecimal.valueOf(150))
                .setHalftime(BigDecimal.valueOf(90))
                .setOvertime(BigDecimal.valueOf(180))
                .build();

        priceDAO.create(price1);
        priceDAO.create(price2);

        price1.setFulltime(BigDecimal.valueOf(150));
        price1.setHalftime(BigDecimal.valueOf(90));
        price1.setOvertime(BigDecimal.valueOf(180));

        assertFalse(priceDAO.update(price1));
    }
    @Test
    public void testSetUsingCache() throws EntityException {
        priceDAO.setUsingCache(false);

        Price price = new Price.PriceBuilder()
                .setId(16)
                .setFulltime(BigDecimal.valueOf(100))
                .setHalftime(BigDecimal.valueOf(60))
                .setOvertime(BigDecimal.valueOf(120))
                .build();

        priceDAO.create(price);

        priceDAO.findById(price.getId());
        Price foundPrice = priceDAO.list().get(0);


        assertNotNull(foundPrice);

        priceDAO.setUsingCache(true);

        foundPrice = priceDAO.findById(price.getId());

        assertEquals(foundPrice, price);
    }

}