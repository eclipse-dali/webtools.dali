/*******************************************************************************
 * Copyright (c) 2001, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * Code originate from org.eclipse.wst.xml.ui.internal.dialogs
 *******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import java.util.Collection;
import java.util.List;
import java.util.Vector;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.dbws.eclipselink.ui.JptDbwsEclipseLinkUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.xml.core.internal.XMLCorePlugin;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalog;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalogEntry;
import org.eclipse.wst.xml.core.internal.catalog.provisional.INextCatalog;


public class SelectXMLCatalogIdPanel extends Composite {
	protected int catalogEntryType;
	protected boolean doTableSizeHack = false;

	protected XMLCatalogTableViewer tableViewer;
	protected ICatalog fXmlCatalog;

	public SelectXMLCatalogIdPanel(Composite parent, ICatalog xmlCatalog, ResourceManager resourceManager) {
		super(parent, SWT.NONE);
		this.fXmlCatalog = xmlCatalog;

		GridLayout gridLayout = new GridLayout();
		this.setLayout(gridLayout);
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 200;
		gd.widthHint = 700;
		this.setLayoutData(gd);

		Label label = new Label(this, SWT.NONE);
		label.setText(JptDbwsEclipseLinkUiMessages.BUILDER_XML_WIZARD_PAGE__XML_CATALOG_TABLE_TITLE);

		tableViewer = createTableViewer(this, resourceManager);
		tableViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));
		tableViewer.setInput("dummy"); //$NON-NLS-1$
	}

	protected XMLCatalogTableViewer createTableViewer(Composite parent, ResourceManager resourceManager) {
		String headings[] = new String[2];
		headings[0] = JptDbwsEclipseLinkUiMessages.BUILDER_XML_WIZARD_PAGE__XML_CATALOG_KEY_COLUMN;
		headings[1] = JptDbwsEclipseLinkUiMessages.BUILDER_XML_WIZARD_PAGE__XML_CATALOG_URI_COLUMN;

		XMLCatalogTableViewer theTableViewer = new XMLCatalogTableViewer(parent, headings, resourceManager) {

			protected void addXMLCatalogEntries(List list, ICatalogEntry[] entries) {
				for (int i = 0; i < entries.length; i++) {
					ICatalogEntry entry = entries[i];
					if (catalogEntryType == 0) {
						list.add(entry);
					}
					else if (catalogEntryType == entry.getEntryType()) {
						list.add(entry);
					}
				}
			}

			public Collection getXMLCatalogEntries() {
				List result = null;

				if ((fXmlCatalog == null) || doTableSizeHack) {
					// this lets us create a table with an initial height of
					// 10 rows
					// otherwise we get stuck with 0 row heigh table... that's
					// too small
					doTableSizeHack = false;
					result = new Vector();
					for (int i = 0; i < 6; i++) {
						result.add(""); //$NON-NLS-1$
					}
				}
				else {
					result = new Vector();
					INextCatalog[] nextCatalogs = fXmlCatalog.getNextCatalogs();
					for (int i = 0; i < nextCatalogs.length; i++) {
						INextCatalog catalog = nextCatalogs[i];
						ICatalog referencedCatalog = catalog.getReferencedCatalog();
						if (referencedCatalog != null) {
							if (XMLCorePlugin.SYSTEM_CATALOG_ID.equals(referencedCatalog.getId())) {
								ICatalog systemCatalog = referencedCatalog;
								addXMLCatalogEntries(result, systemCatalog.getCatalogEntries());

							}
							else if (XMLCorePlugin.USER_CATALOG_ID.equals(referencedCatalog.getId())) {
								ICatalog userCatalog = referencedCatalog;
								addXMLCatalogEntries(result, userCatalog.getCatalogEntries());

							}
						}
					}
				}
				return result;
			}
		};
		return theTableViewer;
	}


	public String getId() {
		ICatalogEntry entry = getXMLCatalogEntry();
		return entry != null ? entry.getKey() : null;
	}

	public XMLCatalogTableViewer getTableViewer() {
		return tableViewer;
	}

	public String getURI() {
		ICatalogEntry entry = getXMLCatalogEntry();
		return entry != null ? entry.getURI() : null;
	}

	public ICatalogEntry getXMLCatalogEntry() {
		ICatalogEntry result = null;
		ISelection selection = tableViewer.getSelection();
		Object selectedObject = (selection instanceof IStructuredSelection) ? ((IStructuredSelection) selection).getFirstElement() : null;
		if (selectedObject instanceof ICatalogEntry) {
			result = (ICatalogEntry) selectedObject;
		}
		return result;
	}

	public void setCatalogEntryType(int catalogEntryType) {
		this.catalogEntryType = catalogEntryType;
		tableViewer.refresh();
	}
}
