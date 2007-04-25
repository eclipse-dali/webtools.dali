/*******************************************************************************
 * Copyright (c) 2006 Versant. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Versant. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

/**
 * A wrapper for a <code>PageBook</code> with convenience methods.
 */
public class PageBookManager extends PageBook {

	private Map pageRecords;
	private Object active;
	private PageBook pageBook;
	private Composite defaultComposite;
	
	/**
	 * A <code>PageBookManager<code> is a wrapper for a <code>PageBook</code>. It provides
	 * convenience methods to add, remove, activate and inactive pages in the internal <code>PageBook</code>.
	 * If a page gets deactivated, the manager activates a default page.
	 * 
	 * @param parent The parent composite to this manager
	 * @param aDefaultLabel The label on the the default page.
	 */
	public PageBookManager(Composite parent, String aDefaultLabel) {
		super(parent, SWT.NULL);
		this.setLayout(new FillLayout());
		this.pageRecords = new HashMap();
		this.pageBook = new PageBook(this, SWT.NONE);
		this.defaultComposite = new DefaultComposite(pageBook, aDefaultLabel);
		this.pageBook.showPage(this.defaultComposite);
	}

	/**
	 * @param anObj Activates (flips to top in the <code>PageBook</code>) the associated <code>Composite</code> 
	 * for the given <code>Object</code>. Activates the <code>DefaultComposite</code> if there is no association
	 * for the given <code>Object</code>.
	 * @return Return false if there is no <code>Composite</code> association to the given <code>Object</code> or
	 * the associated <code>Composite</code> is already active.
	 */
	public boolean activate(Object anObj) {
		if(anObj != null && !anObj.equals(this.active) && this.pageRecords.containsKey(anObj)) {
			Composite composite = (Composite) this.pageRecords.get(anObj);
			this.pageBook.showPage(composite);
			this.active = anObj;
			return true;
		} else if((anObj == null || !anObj.equals(this.active)) && !this.pageRecords.containsKey(anObj)) {
			this.pageBook.showPage(this.defaultComposite);
			this.active = null;
		}
		return false;
	}
	
	/**
	 * @param anObj
	 * @return Returns true if the associated <code>Composite</code> has been deactivated.
	 * Returns false if there is no <code>Composite</code> for the given <code>Object</code>
	 * or the <code>Composite</code> is already inactive.
	 */
	public boolean deactivate(Object anObj) {
		if(anObj.equals(this.active)) {
			this.pageBook.showPage(this.defaultComposite);
			this.active = null;
			return true;
		} else {
			return false;
		}
	}

	/**
     * Associates the specified <code>Object</code> with the specified <code>Composite</code>
     * in this <code>PageBookManager</code>.
     * If this manager previously contained a mapping for the <code>Object</code>, the old
     * <code>Composite</code> is replaced by the specified <code>Composite</code>.
     * 
	 * @param anObj <code>Object</code> with which the specified <code>Composite</code> is to be associated.
	 * @param aComposite <code>Composite</code> to be associated with the specified <code>Object</code>.
	 * 
     * @return previous <code>Composite</code> associated with specified <code>Object</code>,
     * 		   or <tt>null</tt> if there was no mapping for <code>Object</code>.
	 */
	public Composite put(Object anObj, Composite aComposite) {
		Composite composite = (Composite) this.pageRecords.put(anObj, aComposite);
		this.activate(anObj);
		return composite;
	}
	
    /**
     * Removes the mapping for this <code>Object</code> from this pagebookmanager if it is present.
     *
     * @param anObj <code>Object</code> whose mapping is to be removed from this <code>PageBookManager</code>.
     * @return previous <code>Composite</code> associated with specified <code>Object</code>.
     */
	public Composite remove(Object anObj) {
		if(anObj.equals(this.active)) {
			this.active = null;
			this.pageBook.showPage(this.defaultComposite);
		}	
		return (Composite) this.pageRecords.remove(anObj);
	}

	/**
	 * @param anObj whose presence in this map is to be tested
	 * @return true if this <code>PageBookManager</code> contains a mapping for the
	 * 		   given <code>Object</code>. False otherwise.
	 */
	public boolean contains(Object anObj) {
		return this.pageRecords.containsKey(anObj);
	}
	
	public Composite get(Object key) {
		return (Composite) pageRecords.get(key);
	}

	/**
	 * @return The currently active composite
	 */
	public Composite getActive() {
		if(this.active == null) {
			return this.defaultComposite;
		} else {
			return (Composite) this.pageRecords.get(this.active);
		}
	}

	/**
	 * @return The internal <code>PageBook</code> of this <code>PageBookManager</code>. 
	 * <code>Composite</code>s which should work with this manager needs to be created
	 * with this <code>Composite</code> as their parent composite.
	 */
	public Composite getComposite() {
		return this.pageBook;
	}

	// a default composite which is show if the active composite
	// gets deactivated
	private class DefaultComposite extends Composite {
		public DefaultComposite(Composite aParent, String aDefaultLabel) {
			super(aParent, SWT.NONE);
			setLayout(new FillLayout(SWT.VERTICAL));
			Label label = new Label(this, SWT.LEFT);
			label.setText(aDefaultLabel);
		}
	}
	
	public void dispose() {
		super.dispose();
		Collection composites = this.pageRecords.values();
		for (Iterator i = composites.iterator(); i.hasNext(); ) {
			((Composite) i.next()).dispose();
		}
	}
}
