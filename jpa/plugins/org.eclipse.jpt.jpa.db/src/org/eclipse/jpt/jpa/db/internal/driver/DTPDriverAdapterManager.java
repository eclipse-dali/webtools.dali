/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import java.util.HashMap;
import org.eclipse.jpt.jpa.db.Database;

public class DTPDriverAdapterManager {
	private final HashMap<String, DTPDriverAdapterFactory> adapterFactories;

	// singleton
	private static final DTPDriverAdapterManager INSTANCE = new DTPDriverAdapterManager();

	/**
	 * Return the singleton.
	 */
	public static DTPDriverAdapterManager instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private DTPDriverAdapterManager() {
		super();
		this.adapterFactories = this.buildAdapterFactories();
	}

	private HashMap<String, DTPDriverAdapterFactory> buildAdapterFactories() {
		HashMap<String, DTPDriverAdapterFactory> factories = new HashMap<String, DTPDriverAdapterFactory>();
		this.addAdapterFactoriesTo(factories);
		return factories;
	}

	private void addAdapterFactoriesTo(HashMap<String, DTPDriverAdapterFactory> factories) {
		this.addAdapterFactoryTo(new DB2.Factory(), factories);
		this.addAdapterFactoryTo(new Derby.Factory(), factories);
		this.addAdapterFactoryTo(new HSQLDB.Factory(), factories);
		this.addAdapterFactoryTo(new Informix.Factory(), factories);
		this.addAdapterFactoryTo(new MaxDB.Factory(), factories);
		this.addAdapterFactoryTo(new MySQL.Factory(), factories);
		this.addAdapterFactoryTo(new Oracle.Factory(), factories);
		this.addAdapterFactoryTo(new PostgreSQL.Factory(), factories);
		this.addAdapterFactoryTo(new SQLServer.Factory(), factories);
		this.addAdapterFactoryTo(new Sybase.Factory(), factories);
	}

	/**
	 * @see org.eclipse.datatools.modelbase.sql.schema.Database#getVendor()
	 */
	private void addAdapterFactoryTo(DTPDriverAdapterFactory factory, HashMap<String, DTPDriverAdapterFactory> factories) {
		for (String name : factory.getSupportedVendors()) {
			this.addAdapterFactoryTo(name, factory, factories);
		}
	}

	private void addAdapterFactoryTo(String name, DTPDriverAdapterFactory factory, HashMap<String, DTPDriverAdapterFactory> factories) {
		if (factories.put(name, factory) != null) {
			throw new IllegalArgumentException("Duplicate adapter factory: " + name); //$NON-NLS-1$
		}
	}

	public DTPDriverAdapter buildAdapter(String dtpVendorName, Database database) {
		return this.getAdapterFactory(dtpVendorName).buildAdapter(database);
	}

	private DTPDriverAdapterFactory getAdapterFactory(String dtpVendorName) {
		DTPDriverAdapterFactory factory = this.adapterFactories.get(dtpVendorName);
		return (factory != null) ? factory : UNRECOGNIZED_ADAPTER_FACTORY;
	}
	private static final DTPDriverAdapterFactory UNRECOGNIZED_ADAPTER_FACTORY = new Unknown.Factory();
}
