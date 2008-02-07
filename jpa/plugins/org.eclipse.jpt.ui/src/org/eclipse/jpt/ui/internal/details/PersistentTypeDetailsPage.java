/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import java.util.HashMap;
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
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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
	private IJpaComposite<ITypeMapping> currentMappingComposite;
	private String currentMappingKey;
	private Map<String, IJpaComposite<ITypeMapping>> mappingComposites;
	private ComboViewer typeMappingCombo;
	private PageBook typeMappingPageBook;

	/**
	 * Creates a new <code>PersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistentTypeDetailsPage(Composite parent,
                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(IPersistentType.MAPPING_PROPERTY);
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

	private PropertyAspectAdapter<IPersistentType, ITypeMapping> buildGenericMappingHolder() {
		return new PropertyAspectAdapter<IPersistentType, ITypeMapping>(getSubjectHolder(), IPersistentType.MAPPING_PROPERTY) {
			@Override
			protected ITypeMapping buildValue_() {
				return subject.getMapping();
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

	private Filter<ITypeMapping> buildMappingFilter(final String key) {
		return new Filter<ITypeMapping>() {
			public boolean accept(ITypeMapping value) {
				return (value == null) || key.equals(value.getKey());
			}
		};
	}

	private PropertyValueModel<ITypeMapping> buildMappingHolder(String key) {
		return new FilteringPropertyValueModel<ITypeMapping>(
			buildGenericMappingHolder(),
			buildMappingFilter(key)
		);
	}

	protected ComboViewer buildTypeMappingCombo(Composite parent) {
		CCombo combo = buildEditableCCombo(parent);
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
		log(Tracing.UI_DETAILS_VIEW, "PersistentTypeDetailsPage.doDispose()");

		this.currentMappingComposite = null;

		for (IJpaComposite<ITypeMapping> composite : this.mappingComposites.values()) {
			try {
				composite.dispose();
			}
			catch (Exception e) {
				JptUiPlugin.log(e);
			}
		}

		this.mappingComposites.clear();
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.mappingComposites = new HashMap<String, IJpaComposite<ITypeMapping>>();
	}

	private IJpaComposite<ITypeMapping> mappingCompositeFor(String key) {
		IJpaComposite<ITypeMapping> mappingComposite = this.mappingComposites.get(key);
		if (mappingComposite != null) {
			return mappingComposite;
		}

		mappingComposite = buildMappingComposite(this.typeMappingPageBook, key);

		if (mappingComposite != null) {
			this.mappingComposites.put(key, mappingComposite);
		}

		return mappingComposite;
	}

	private void populateMapAsCombo() {
		if (this.subject() != this.typeMappingCombo.getInput()) {
			this.typeMappingCombo.setInput(this.subject());
		}
		if (this.subject() != null) {
			if (this.subject().getMapping() == null) {
				this.typeMappingCombo.setSelection(new StructuredSelection(this.typeMappingCombo.getElementAt(0)));
			}
			else {
				ITypeMappingUiProvider<? extends ITypeMapping> provider = typeMappingUiProvider(this.subject().mappingKey());
				if (provider != null && ! provider.equals(((StructuredSelection) this.typeMappingCombo.getSelection()).getFirstElement())) {
					this.typeMappingCombo.setSelection(new StructuredSelection(provider));
				}
			}
		}
	}

	private void populateMappingComboAndPage() {
		populateMapAsCombo();
		updateMappingPage();
	}

	private void populateMappingPage(String mappingKey) {
		// Nothing to update
		if (this.currentMappingKey == mappingKey) {
			return;
		}
		else if (this.currentMappingComposite != null) {
			this.log(
				Tracing.UI_DETAILS_VIEW,
				"PersistentTypeDetailsPage.populateMappingPage() disposing of current page: " + this.currentMappingKey
			);

			this.currentMappingComposite.dispose();
		}

		this.currentMappingKey = mappingKey;

		if (this.currentMappingKey != null) {
			this.currentMappingComposite = mappingCompositeFor(mappingKey);

			try {
				this.log(
					Tracing.UI_DETAILS_VIEW,
					"PersistentTypeDetailsPage.populateMappingPage() populating new page: " + this.currentMappingKey
				);

				this.currentMappingComposite.populate();
				this.typeMappingPageBook.showPage(this.currentMappingComposite.getControl());
			}
			catch (Exception e) {
				this.log(
					Tracing.UI_DETAILS_VIEW,
					"PersistentTypeDetailsPage.populateMappingPage() error encountered"
				);

				this.mappingComposites.remove(this.currentMappingComposite);
				this.currentMappingComposite = null;
				this.typeMappingPageBook.showPage(new Label(this.typeMappingPageBook, SWT.NULL));
				JptUiPlugin.log(e);
			}
		}
		else {
			this.log(
				Tracing.UI_DETAILS_VIEW,
				"PersistentTypeDetailsPage.populateMappingPage() no page to show"
			);

			this.currentMappingComposite = null;
			this.typeMappingPageBook.showPage(new Label(this.typeMappingPageBook, SWT.NULL));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == IPersistentType.MAPPING_PROPERTY) {
			populateMappingComboAndPage();
		}
	}

	private void typeMappingChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof StructuredSelection) {
			ITypeMappingUiProvider<?> provider = (ITypeMappingUiProvider<?>) ((StructuredSelection) event.getSelection()).getFirstElement();
			this.subject().setMappingKey(provider.mappingKey());
		}
	}

	private ITypeMappingUiProvider<? extends ITypeMapping> typeMappingUiProvider(String key) {
		for (ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> iter = this.typeMappingUiProviders(); iter.hasNext();) {
			ITypeMappingUiProvider<? extends ITypeMapping> provider = iter.next();
			if (provider.mappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported type mapping UI provider key: " + key);
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return typeMappingCombo.getCombo().setFocus();
//	}

	protected abstract ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> typeMappingUiProviders();

	private void updateMappingPage() {
		ITypeMapping mapping = (this.subject() != null) ? this.subject().getMapping() : null;
		populateMappingPage(mapping == null ? null : mapping.getKey());
	}
}