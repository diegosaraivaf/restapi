package com.projeto.util;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

public class HibernateUtil {
	
	public static <T> T unwrapProxy(T object) {
	    if (object instanceof HibernateProxy) {
	        LazyInitializer initializer = ((HibernateProxy) object).getHibernateLazyInitializer();
	        if (initializer.isUninitialized()) {
	            initializer.initialize();
	        }
	        return (T) initializer.getImplementation();
	    }
	    return object;
	}

}
