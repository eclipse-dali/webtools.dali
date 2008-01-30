/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.internal.context.base.IPersistentType;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The abstract definition of the details page responsible to show the
 * information for an persistent type.
 *
 * @see IPersistentType
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class PersistentTypeDetailsPage<T extends IPersistentType> extends BaseJpaDetailsPage<T>
{
	private Map<String, IJpaComposite<ITypeMapping>> composites;
	private IJpaComposite<ITypeMapping> currentMappingComposite;
	private String currentMappingKey;
	private ComboViewer typeMappingCombo;
	private PageBook typeMappingPageBook;

	/**
	 * Creates a new <code>PersistentTypeDetailsPage</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistentTypeDetailsPage(PropertyValueModel<? extends T> subjectHolder,
                                    Composite parent,
                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
		this.composites = new HashMap<String, IJpaComposite<ITypeMapping>>();
	}

	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider() {
			public void dispose() {
				// do nothing
			}

			public Object[] getElements(Object inputElement) {
				return (subject() == null) ?
						new String[] {}:
						CollectionTools.array(PersistentTypeDetailsPage.this.typeMappingUiProviders());
			}

			public void inputChanged(
					Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}

	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((ITypeMappingUiProvider<?>) element).label();
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected IJpaComposite<ITypeMapping> buildMappingComposite(PageBook pageBook,
	                                                            String key)  {

		ITypeMappingUiProvider<ITypeMapping> uiProvider =
			(ITypeMappingUiProvider<ITypeMapping>) typeMappingUiProvider(key);

		return uiProvider.buildPersistentTypeMappingComposite(
			buildMappingHolder(key),
			pageBook,
			getFormWidgetFactory()
		);
	}

	private PropertyValueModel<ITypeMapping> buildMappingHolder(final String key) {
		return new TransformationPropertyValueModel<T, ITypeMapping>(getSubjectHolder()) {
			@Override
			protected ITypeMapping transform_(T value) {
				return key.equals(value.mappingKey()) ? value.getMapping() : null;
			}
		};
	}

	protected ComboViewer buildTypeMappingCombo(Composite parent) {
		CCombo combo = buildCombo(parent);
		this.typeMappingCombo = new ComboViewer(combo);
		this.typeMappingCombo.getCCombo().setVisibleItemCount(Integer.MAX_VALUE);
		this.typeMappingCombo.setContentProvider(buildContentProvider());
		this.typeMappingCombo.setLabelProvider(buildLabelProvider());
		this.typeMappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				typeMappingChanged(event);
			}
		});
		return this.typeMappingCombo;
	}

	protected Label buildTypeMappingLabel(Composite parent) {
		return buildLabel(parent, JptUiMessages.PersistentTypePage_mapAs);
	}

	protected PageBook buildTypeMappingPageBook(Composite parent) {
		this.typeMappingPageBook = new PageBook(parent, SWT.NONE);
		return this.typeMappingPageBook;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doDispose() {

		for (Iterator<IJpaComposite<ITypeMapping>> iter = this.composites.values().iterator(); iter.hasNext(); ) {
			iter.next().dispose();
		}

		super.doDispose();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateMappingComboAndPage();
	}

	private IJpaComposite<ITypeMapping> mappingCompositeFor(String key) {
		IJpaComposite<ITypeMapping> mappingComposite = this.composites.get(key);
		if (mappingComposite != null) {
			return mappingComposite;
		}

		mappingComposite = buildMappingComposite(this.typeMappingPageBook, key);

		if (mappingComposite != null) {
			this.composites.put(key, mappingComposite);
		}

		return mappingComposite;
	}

	private void populateMappingComboAndPage() {
		if (this.subject() == null) {
			this.currentMappingKey = null;
			this.typeMappingCombo.setInput(null);
			this.typeMappingCombo.setSelection(StructuredSelection.EMPTY);

			if (this.currentMappingComposite != null) {
				this.currentMappingComposite.populate();
				this.currentMappingComposite = null;
			}

			return;
		}

		String mappingKey = this.subject().getMapping().getKey();
		setComboData(mappingKey);

		populateMappingPage(mappingKey);
	}

	private void populateMappingPage(String mappingKey) {
		if (this.currentMappingComposite != null &&
		    this.currentMappingKey == mappingKey) {

			this.currentMappingComposite.populate();
			return;
		}

		this.currentMappingKey = mappingKey;

		if (this.currentMappingKey != null) {
			this.currentMappingComposite = mappingCompositeFor(mappingKey);

			try {
				this.currentMappingComposite.populate();
			}
			finally {
				// Log or show error
			}

			this.typeMappingPageBook.showPage(this.currentMappingComposite.getControl());
			this.typeMappingPageBook.layout(true);
		}
		else {
			this.currentMappingComposite = null;
			this.typeMappingPageBook.showPage(new Label(this.typeMappingPageBook, SWT.NULL));
		}
	}

	private void setComboData(String mappingKey) {
		if (this.subject() != this.typeMappingCombo.getInput()) {
			this.typeMappingCombo.setInput(this.subject());
		}

		ITypeMappingUiProvider<? extends ITypeMapping> provider = typeMappingUiProvider(mappingKey);
		if (! provider.equals(((StructuredSelection) this.typeMappingCombo.getSelection()).getFirstElement())) {
			this.typeMappingCombo.setSelection(new StructuredSelection(provider));
		}
	}

	private void typeMappingChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof StructuredSelection) {
			ITypeMappingUiProvider<?> provider = (ITypeMappingUiProvider<?>) ((StructuredSelection) event.getSelection()).getFirstElement();
			this.subject().setMappingKey(provider.mappingKey());
		}
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return typeMappingCombo.getCombo().setFocus();
//	}

	private ITypeMappingUiProvider<? extends ITypeMapping> typeMappingUiProvider(String key) {
		for (ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> iter = this.typeMappingUiProviders(); iter.hasNext();) {
			ITypeMappingUiProvider<? extends ITypeMapping> provider = iter.next();
			if (provider.mappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping UI provider key: " + key);
	}

	protected abstract ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> typeMappingUiProviders();
}