/*******************************************************************************
 * Copyright (c) 2001, 2012 IBM Corporation and others.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.internal.jface.OverlayImageDescriptor;
import org.eclipse.jpt.common.ui.internal.jface.PluggableTextTableLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.ResourceManagerTableLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.dbws.eclipselink.ui.JptDbwsEclipseLinkUiImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wst.common.uriresolver.internal.util.URIHelper;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalogEntry;
import com.ibm.icu.text.Collator;

public abstract class XMLCatalogTableViewer
	extends TableViewer
{
	protected static String ERROR_STATE_KEY = "errorstatekey"; //$NON-NLS-1$


	protected XMLCatalogTableViewer(Composite parent, String[] columnProperties, ResourceManager resourceManager) {
		super(parent, SWT.FULL_SELECTION);

		Table table = getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableLayout layout = new TableLayout();
		for (int i = 0; i < columnProperties.length; i++) {
			TableColumn column = new TableColumn(table, i);
			column.setText(columnProperties[i]);
			column.setAlignment(SWT.LEFT);
			layout.addColumnData(new ColumnWeightData(50, true));
		}
		table.setLayout(layout);
		table.setLinesVisible(false);

		setColumnProperties(columnProperties);

		setContentProvider(new CatalogEntryContentProvider());
		setLabelProvider(this.buildLabelProvider(resourceManager));
	}

	protected IBaseLabelProvider buildLabelProvider(ResourceManager resourceManager) {
		return new ResourceManagerTableLabelProvider<Object>(
				CATALOG_ENTRY_LABEL_IMAGE_DESCRIPTOR_TRANSFORMER,
				CATALOG_ENTRY_LABEL_TEXT_TRANSFORMER,
				resourceManager
			);
	}

	protected abstract Collection getXMLCatalogEntries();

	public void setFilterExtensions(String[] extensions) {
		resetFilters();
		addFilter(new XMLCatalogTableViewerFilter(extensions));
	}


	// ********** content provider **********

	public class CatalogEntryContentProvider
		implements IStructuredContentProvider
	{
		public Object[] getElements(Object element) {
			Object[] array = getXMLCatalogEntries().toArray();
			Comparator comparator = new Comparator() {
				public int compare(Object o1, Object o2) {
					int result = 0;
					if ((o1 instanceof ICatalogEntry) && (o2 instanceof ICatalogEntry)) {
						ICatalogEntry mappingInfo1 = (ICatalogEntry) o1;
						ICatalogEntry mappingInfo2 = (ICatalogEntry) o2;
						result = Collator.getInstance().compare(mappingInfo1.getKey(), mappingInfo2.getKey());
					}
					return result;
				}
			};
			Arrays.sort(array, comparator);
			return array;
		}

		public void inputChanged(Viewer viewer, Object old, Object newobj) {
			// NOP
		}

		public boolean isDeleted(Object object) {
			return false;
		}

		public void dispose() {
			// NOP
		}
	}


	// ********** label provider **********

	protected static final PluggableTextTableLabelProvider.TextTransformer<Object> CATALOG_ENTRY_LABEL_TEXT_TRANSFORMER = new CatalogEntryLabelTextTransformer();
	protected static class CatalogEntryLabelTextTransformer
		implements PluggableTextTableLabelProvider.TextTransformer<Object>
	{
		public String transform(Object element, int columnIndex) {
			return (element instanceof ICatalogEntry) ? this.transform((ICatalogEntry) element, columnIndex) : null;
		}

		protected String transform(ICatalogEntry catalogEntry, int columnIndex) {
			return (columnIndex == 0) ?
					catalogEntry.getKey() :
					URIHelper.removePlatformResourceProtocol(catalogEntry.getURI());
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	protected static final ResourceManagerTableLabelProvider.ImageDescriptorTransformer<Object> CATALOG_ENTRY_LABEL_IMAGE_DESCRIPTOR_TRANSFORMER = new CatalogEntryLabelImageDescriptorTransformer();
	protected static class CatalogEntryLabelImageDescriptorTransformer
		implements ResourceManagerTableLabelProvider.ImageDescriptorTransformer<Object>
	{
		public ImageDescriptor transform(Object element, int columnIndex) {
			return (element instanceof ICatalogEntry) ? this.transform((ICatalogEntry) element, columnIndex) : null;
		}

		protected ImageDescriptor transform(ICatalogEntry catalogEntry, int columnIndex) {
			return (columnIndex == 0) ? this.transform(catalogEntry) : null;
		}

		protected ImageDescriptor transform(ICatalogEntry catalogEntry) {
			String uri = catalogEntry.getURI();
			if (uri == null) {
				return null;
			}
			ImageDescriptor base = this.getBaseColumnImageDescriptor(uri);
			return URIHelper.isReadableURI(uri, false) ? base : this.buildErrorImageDescriptor(base);
		}

		protected ImageDescriptor getBaseColumnImageDescriptor(String uri) {
			uri = uri.toLowerCase();
			if (uri.endsWith("dtd")) { //$NON-NLS-1$
				return JptDbwsEclipseLinkUiImages.DTD_FILE;
			}
			if (uri.endsWith("xsd")) { //$NON-NLS-1$
				return JptDbwsEclipseLinkUiImages.XSD_FILE;
			}
			return JptCommonUiImages.FILE;
		}

		protected ImageDescriptor buildErrorImageDescriptor(ImageDescriptor imageDescriptor) {
			return new OverlayImageDescriptor(imageDescriptor, JptCommonUiImages.ERROR_OVERLAY, IDecoration.BOTTOM_LEFT);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	class XMLCatalogTableViewerFilter extends ViewerFilter {
		protected String[] extensions;

		public XMLCatalogTableViewerFilter(String[] extensions) {
			this.extensions = extensions;
		}

		public boolean isFilterProperty(Object element, Object property) {
			return false;
		}

		public boolean select(Viewer viewer, Object parent, Object element) {
			boolean result = false;
			if (element instanceof ICatalogEntry) {
				ICatalogEntry catalogEntry = (ICatalogEntry) element;
				for (int i = 0; i < extensions.length; i++) {
					if (catalogEntry.getURI().endsWith(extensions[i])) {
						result = true;
						break;
					}
				}
			}
			return result;
		}
	}
}
