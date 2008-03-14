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
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;

/**
 * The default implementation of the details page used for the XML persistent
 * attribute.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmJavaClassChooser                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |         ----------------------------------------------------------------- |
 * | Map As: |                                                             |v| |
 * |         ----------------------------------------------------------------- |
 * |                                                                           |
 * | X Metadata Complete                                                       |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrmJavaClassChooser                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | Type mapping pane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see OrmPersistentType
 * @see OrmJavaClassChooser
 * @see AccessTypeComposite
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
	                                    WidgetFactory widgetFactory) {

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

	private WritablePropertyValueModel<Boolean> buildMetadataCompleteHolder() {
		return new PropertyAspectAdapter<OrmTypeMapping, Boolean>(
			buildMappingHolder(),
			OrmTypeMapping.DEFAULT_METADATA_COMPLETE_PROPERTY,
			OrmTypeMapping.SPECIFIED_METADATA_COMPLETE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedMetadataComplete();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedMetadataComplete(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.value();
				super.subjectChanged();
				Object newValue = this.value();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildMetadataCompleteStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildMetadataCompleteHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					boolean defaultValue = subject().getMapping().isDefaultMetadataComplete();
					String defaultStringValue = defaultValue ? JptUiOrmMessages.Boolean_True :
						                                           JptUiOrmMessages.Boolean_False;

					return NLS.bind(
						JptUiOrmMessages.OrmPersistentTypeDetailsPage_metadataCompleteWithDefault,
						defaultStringValue
					);
				}

				return JptUiOrmMessages.OrmPersistentTypeDetailsPage_metadataComplete;
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

		// Metadata complete widgets
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiOrmMessages.OrmPersistentTypeDetailsPage_metadataComplete,
			buildMetadataCompleteHolder(),
			buildMetadataCompleteStringHolder()
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