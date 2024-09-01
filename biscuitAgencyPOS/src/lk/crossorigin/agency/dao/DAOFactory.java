package lk.crossorigin.agency.dao;

import lk.crossorigin.agency.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }

    public static DAOFactory getInstance(){
        if(daoFactory==null){
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public enum DaoType{
        DISCOUNT,ITEM,ORDERBOOK,ORDER,ORDERDETAILS,PAYMENT,RETURN,SHOP,MainITEM,ShopCredit,SAVELIST,MONTHLYREPORT,ORDERHISTORY;
    }

    public <T> T getDao(DaoType type){
        switch (type){
            case DISCOUNT:
                return (T) new DiscountDaoImpl();
            case ITEM:
                return (T) new ItemDaoImpl();
            case ORDERBOOK:
                return (T) new OrderBookDaoimpl();
            case ORDER:
                return (T) new OrderDaoImpl();
            case ORDERDETAILS:
                return (T) new OrderDetailsDaoImpl();
            case PAYMENT:
                return (T) new  PaymentDaoImpl();
            case RETURN:
                return (T) new ReturnDaoImpl();
            case SHOP:
                return (T) new  ShopDaoImpl();
            case MainITEM:
                return (T) new  MainItemDaoImpl();
            case ShopCredit:
                return (T) new ShopCreditDaoImpl();
            case SAVELIST:
                return (T) new SaveListDaoImpl();
            case MONTHLYREPORT:
                return (T) new MonthlyReportDaoImpl();
            case ORDERHISTORY:
                return (T) new OrderHistoryDaoImpl();
            default:
                return null;
        }
    }
}