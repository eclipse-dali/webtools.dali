/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
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
import org.eclipse.jpt.core.internal.context.base.IAttributeMapping;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.IJpaUiFactory;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.java.details.IAttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.platform.base.BaseJpaPlatformUi;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.model.value.FilteringPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The abstract definition of the details page responsible to show the
 * information for an persistent attribute.
 *
 * @see IPersistentAttribute
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class PersistentAttributeDetailsPage<T extends IPersistentAttribute> extends BaseJpaDetailsPage<T>
{
	private IJpaComposite<IAttributeMapping> currentMappingComposite;
	private String currentMappingKey;
	private ComboViewer mappingCombo;
	private Map<String, IJpaComposite<IAttributeMapping>> mappingComposites;
	protected PageBook mappingPageBook;

	/**
	 * Creates a new <code>PersistentAttributeDetailsPage</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PersistentAttributeDetailsPage(PropertyValueModel<? extends T> subjectHolder,
                                         Composite parent,
                                         TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(IPersistentAttribute.DEFAULT_MAPPING_PROPERTY);
		propertyNames.add(IPersistentAttribute.SPECIFIED_MAPPING_PROPERTY);
	}

	protected IAttributeMappingUiProvider<? extends IAttributeMapping> attributeMappingUiProvider(String key) {
		for (ListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>> i = attributeMappingUiProviders(); i.hasNext(); ) {
			IAttributeMappingUiProvider<? extends IAttributeMapping> provider = i.next();
			if (provider.attributeMappingKey() == key) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Unsupported attribute mapping UI provider key: ");
	}

	protected abstract ListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>>
		attributeMappingUiProviders();

	protected abstract IAttributeMappingUiProvider<? extends IAttributeMapping>[]
		attributeMappingUiProvidersFor(IPersistentAttribute persistentAttribute);

	private IContentProvider buildContentProvider() {
		return new IStructuredContentProvider() {
			public void dispose() {
				// do nothing
			}

			public Object[] getElements(Object inputElement) {
				if (inputElement == null) {
					return new Object[0];
				}
				return attributeMappingUiProvidersFor((IPersistentAttribute) inputElement);
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}

	private PropertyAspectAdapter<IPersistentAttribute, IAttributeMapping> buildGenericMappingHolder() {
		return new PropertyAspectAdapter<IPersistentAttribute, IAttributeMapping>(
			getSubjectHolder(),
			IPersistentAttribute.DEFAULT_MAPPING_PROPERTY,
			IPersistentAttribute.SPECIFIED_MAPPING_PROPERTY)
		{
			@Override
			protected IAttributeMapping buildValue_() {
				return subject.getMapping();
			}
		};
	}

	private IBaseLabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((IAttributeMappingUiProvider<?>) element).label();
			}
		};
	}

	protected ComboViewer buildMappingCombo(Composite parent) {

		this.mappingCombo = buildComboViewer(parent, buildLabelProvider());
		this.mappingCombo.getCCombo().setVisibleItemCount(Integer.MAX_VALUE);
		this.mappingCombo.setContentProvider(buildContentProvider());
		this.mappingCombo.addSelectionChangedListener(buildMappingComboModifyListener());
		return this.mappingCombo;
	}

	private ISelectionChangedListener buildMappingComboModifyListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				mappingChanged(e);
			}
		};
	}

	@SuppressWarnings("unchecked")
	protected IJpaComposite<IAttributeMapping> buildMappingComposite(PageBook pageBook,
	                                                                 String key) {

		IAttributeMappingUiProvider<IAttributeMapping> uiProvider = (IAttributeMappingUiProvider<IAttributeMapping>) mappingUIProvider(key);

		return uiProvider.buildAttributeMappingComposite(
			jpaUiFactory(),
			buildMappingHolder(key),
			pageBook,
			getFormWidgetFactory()
		);
	}

	private Filter<IAttributeMapping> buildMappingFilter(final String key) {
		return new Filter<IAttributeMapping>() {
			public boolean accept(IAttributeMapping value) {
				return (value == null) || key.equals(value.getKey());
			}
		};
	}

	private PropertyValueModel<IAttributeMapping> buildMappingHolder(final String key) {
		return new FilteringPropertyValueModel<IAttributeMapping>(
			buildGenericMappingHolder(),
			buildMappingFilter(key)
		);
	}

	protected Label buildMappingLabel(Composite parent) {
		return buildLabel(parent, JptUiMessages.PersistentAttributePage_mapAs);
	}

	protected PageBook buildMappingPageBook(Composite parent) {
		this.mappingPageBook = new PageBook(parent, SWT.NONE);
		return this.mappingPageBook;
	}

	protected abstract IAttributeMappingUiProvider<? extends IAttributeMapping>
		defaultAttributeMappingUiProvider(String key);

	protected abstract ListIterator<IAttributeMappingUiProvider<? extends IAttributeMapping>>
		defaultAttributeMappingUiProviders();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doDispose() {
		this.currentMappingComposite = null;

		for (IJpaComposite<IAttributeMapping> composite : this.mappingComposites.values()) {
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
		this.mappingComposites = new HashMap<String, IJpaComposite<IAttributeMapping>>();
	}

	protected IJpaPlatformUi jpaPlatformUi() {
		String platformId = subject().jpaProject().jpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
	}

	protected IJpaUiFactory jpaUiFactory() {
		// TODO: Remove the type cast, used for testing
		return ((BaseJpaPlatformUi) jpaPlatformUi()).getJpaUiFactory();
	}

	void mappingChanged(SelectionChangedEvent event) {
		if (event.getSelection() instanceof StructuredSelection) {
			IAttributeMappingUiProvider<?> provider = (IAttributeMappingUiProvider<?>) ((StructuredSelection) event.getSelection()).getFirstElement();
			String key = (CollectionTools.contains(defaultAttributeMappingUiProviders(), provider) ? null : provider.attributeMappingKey());
			this.subject().setSpecifiedMappingKey(key);
		}
	}

	private IJpaComposite<IAttributeMapping> mappingCompositeFor(String key) {
		IJpaComposite<IAttributeMapping> composite = this.mappingComposites.get(key);
		if (composite != null) {
			return composite;
		}

		composite = buildMappingComposite(this.mappingPageBook, key);

		if (composite != null) {
			this.mappingComposites.put(key, composite);
		}

		return composite;
	}

	private IAttributeMappingUiProvider<? extends IAttributeMapping> mappingUIProvider(String key) {

		if (this.subject().getMapping() == null ||
		    this.subject().getMapping().isDefault()) {

			return defaultAttributeMappingUiProvider(key);
		}

		return attributeMappingUiProvider(key);
	}

	private void populateMapAsCombo() {
		if (this.subject() != this.mappingCombo.getInput()) {
			this.mappingCombo.setInput(this.subject());
		}
		if (this.subject() != null) {
			if (this.subject().getMapping() == null || this.subject().getMapping().isDefault()) {
				this.mappingCombo.setSelection(new StructuredSelection(this.mappingCombo.getElementAt(0)));
			}
			else {
				IAttributeMappingUiProvider<? extends IAttributeMapping> provider = attributeMappingUiProvider(this.subject().mappingKey());
				if (provider != null && ! provider.equals(((StructuredSelection) this.mappingCombo.getSelection()).getFirstElement())) {
					this.mappingCombo.setSelection(new StructuredSelection(provider));
				}
			}
		}
	}

	private void populateMappingComboAndPage() {
		populateMapAsCombo();
		updateMappingPage();
	}

	private void populateMappingPage(String mappingKey) {

		if (this.currentMappingComposite != null) {

			if (this.currentMappingKey != mappingKey) {
				this.currentMappingComposite.dispose();
			}
			else {
				return;
			}
		}

		this.currentMappingKey = mappingKey;

		if (this.currentMappingKey != null) {
			this.currentMappingComposite = mappingCompositeFor(mappingKey);

			try {
				this.currentMappingComposite.populate();
				this.mappingPageBook.showPage(this.currentMappingComposite.getControl());
				this.mappingPageBook.layout(true);
			}
			catch (Exception e) {
				this.mappingComposites.remove(this.currentMappingComposite);
				this.currentMappingComposite = null;
				this.mappingPageBook.showPage(new Label(this.mappingPageBook, SWT.NULL));
				JptUiPlugin.log(e);
			}
		}
		else {
			this.currentMappingComposite = null;
			this.mappingPageBook.showPage(new Label(this.mappingPageBook, SWT.NULL));
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == IPersistentAttribute.DEFAULT_MAPPING_PROPERTY ||
		    propertyName == IPersistentAttribute.SPECIFIED_MAPPING_PROPERTY) {

			populateMappingComboAndPage();
		}
	}

//TODO focus??
//	public boolean setFocus() {
//		super.setFocus();
//		return mappingCombo.getCombo().setFocus();
//	}

	private void updateMappingPage() {
		IAttributeMapping mapping = (this.subject() != null) ? this.subject().getMapping() : null;
		populateMappingPage(mapping == null ? null : mapping.getKey());
	}
}