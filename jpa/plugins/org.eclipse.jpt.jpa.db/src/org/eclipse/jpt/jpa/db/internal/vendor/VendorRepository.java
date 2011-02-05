/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.vendor;

import java.util.ArrayList;
import java.util.Iterator;

public class VendorRepository {
	private final Vendor[] vendors;

	// singleton
	private static final VendorRepository INSTANCE = new VendorRepository();

	/**
	 * Return the singleton.
	 */
	public static VendorRepository instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private VendorRepository() {
		super();
		this.vendors = this.buildVendors();
	}

	private Vendor[] buildVendors() {
		ArrayList<Vendor> list = new ArrayList<Vendor>();
		this.addVendorsTo(list);
		return list.toArray(new Vendor[list.size()]);
	}

	private void addVendorsTo(ArrayList<Vendor> list) {
		this.addVendorTo(DB2.udb(), list);
		this.addVendorTo(DB2.udbISeries(), list);
		this.addVendorTo(DB2.udbZSeries(), list);
		this.addVendorTo(Derby.instance(), list);
		this.addVendorTo(HSQLDB.instance(), list);
		this.addVendorTo(Informix.instance(), list);
		this.addVendorTo(MaxDB.instance(), list);
		this.addVendorTo(MySQL.instance(), list);
		this.addVendorTo(Oracle.instance(), list);
		this.addVendorTo(PostgreSQL.instance(), list);
		this.addVendorTo(SQLServer.instance(), list);
		this.addVendorTo(Sybase.asa(), list);
		this.addVendorTo(Sybase.ase(), list);
	}

	private void addVendorTo(Vendor vendor, ArrayList<Vendor> list) {
		String name = vendor.getDTPVendorName();
		for (Iterator<Vendor> stream = list.iterator(); stream.hasNext(); ) {
			if (stream.next().getDTPVendorName().equals(name)) {
				throw new IllegalArgumentException("Duplicate vendor: " + name); //$NON-NLS-1$
			}
		}
		list.add(vendor);
	}

	public Vendor getVendor(String dtpVendorName) {
		for (int i = this.vendors.length; i-- > 0;) {
			Vendor vendor = this.vendors[i];
			if (vendor.getDTPVendorName().equals(dtpVendorName)) {
				return vendor;
			}
		}
		return UnrecognizedVendor.instance();
	}

}
