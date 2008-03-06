/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.details.EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.details.MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The default implementation of the details page used for the XML persistent
 * attribute.
 *
 * @see OrmPersistentType
 *
 * @version 2.0
 * @since 2.0
 */
public class OrmPersistentTypeDetailsPage extends PersistentTypeDetailsPage<OrmPersistentType>
{
	/**
	 * Storing these here instead of querying IJpaPlatformUI, because the orm.xml
	 * schema is not extensible. We only need to support extensibility for java.
	 */
	private List<TypeMappingUiProvider<? extends TypeMapping>> ormTypeMappingUiProviders;

	/**
	 * Creates a new <code>OrmPersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmPersistentTypeDetailsPage(Composite parent,
	                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	protected void addOrmTypeMappingUiProvidersTo(Collection<TypeMappingUiProvider<? extends TypeMapping>> providers) {
		providers.add(OrmEntityUiProvider.instance());
		providers.add(MappedSuperclassUiProvider.instance());
		providers.add(EmbeddableUiProvider.instance());
	}

	private PropertyValueModel<OrmTypeMapping> buildMappingHolder() {
		return new PropertyAspectAdapter<OrmPersistentType,  OrmTypeMapping>(getSubjectHolder(), PersistentType.MAPPING_PROPERTY) {
			@Override
			protected OrmTypeMapping buildValue_() {
				return subject.getMapping();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Java class widgets
		new OrmJavaClassChooser(this, buildMappingHolder(), container);

		// Type Mapping widgets
		ComboViewer typeMappingCombo = buildTypeMappingCombo(container);

		buildLabeledComposite(
			container,
			JptUiMessages.PersistentTypePage_mapAs,
			typeMappingCombo.getControl().getParent()
		);

		// Metadata complete widget
		MetaDataCompleteComboViewer metadataCompleteComboViewer =
			new MetaDataCompleteComboViewer(this, buildMappingHolder(), container);

		buildLabeledComposite(
			container,
			JptUiOrmMessages.PersistentTypePage_MetadataCompleteLabel,
			metadataCompleteComboViewer.getControl()
		);

		// Access widgets
		new AccessTypeComposite(this, buildMappingHolder(), container);

		// Type mapping pane
		PageBook typeMappingPageBook = buildTypeMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.TOP;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		typeMappingPageBook.setLayoutData(gridData);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public ListIterator<TypeMappingUiProvider<? extends TypeMapping>> typeMappingUiProviders() {
		if (this.ormTypeMappingUiProviders == null) {
			this.ormTypeMappingUiProviders = new ArrayList<TypeMappingUiProvider<? extends TypeMapping>>();
			this.addOrmTypeMappingUiProvidersTo(this.ormTypeMappingUiProviders);
		}
		return new CloneListIterator<TypeMappingUiProvider<? extends TypeMapping>>(this.ormTypeMappingUiProviders);
	}
}