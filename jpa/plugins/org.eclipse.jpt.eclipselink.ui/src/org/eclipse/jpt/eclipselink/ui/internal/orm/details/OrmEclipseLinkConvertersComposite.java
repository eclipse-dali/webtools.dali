/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkCustomConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkObjectTypeConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkStructConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkTypeConverterComposite;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * This pane shows the list of named queries and named native queries.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ConverterComposite or ObjectTypeConverterComposite                    | |
 * | | or StructConverterComposite or TypeConverterComposite                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see Query
 * @see NamedNativeQuery
 * @see NamedQuery
 * @see AbstractEntityComposite - The parent container
 * @see NamedNativeQueryPropertyComposite
 * @see NamedQueryPropertyComposite
 *
 * @version 2.1
 * @since 2.1
 */
public class OrmEclipseLinkConvertersComposite extends Pane<EclipseLinkConverterHolder>
{
	private AddRemoveListPane<EclipseLinkConverterHolder> listPane;
	private EclipseLinkCustomConverterComposite converterComposite;
	private EclipseLinkObjectTypeConverterComposite objectTypeConverterComposite;
	private EclipseLinkStructConverterComposite structConverterComposite;
	private EclipseLinkTypeConverterComposite typeConverterComposite;
	private WritablePropertyValueModel<EclipseLinkConverter> selectedConverterHolder;

	public OrmEclipseLinkConvertersComposite(
		FormPane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkConverterHolder> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent, false);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedConverterHolder = buildSelectedConverterHolder();
	}

	private WritablePropertyValueModel<EclipseLinkConverter> buildSelectedConverterHolder() {
		return new SimplePropertyValueModel<EclipseLinkConverter>();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// List pane
		this.listPane = addListPane(container);

		// Property pane
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.converterComposite = new EclipseLinkCustomConverterComposite(
			buildCustomConverterHolder(),
			pageBook,
			getWidgetFactory()
		);
		registerSubPane(this.converterComposite);
		
		this.objectTypeConverterComposite = new EclipseLinkObjectTypeConverterComposite(
			buildObjectTypeConverterHolder(),
			pageBook,
			getWidgetFactory()
		);
		registerSubPane(this.objectTypeConverterComposite);
		
		this.structConverterComposite = new EclipseLinkStructConverterComposite(
			buildStructConverterHolder(),
			pageBook,
			getWidgetFactory()
		);
		registerSubPane(this.structConverterComposite);
		
		this.typeConverterComposite = new EclipseLinkTypeConverterComposite(
			buildTypeConverterHolder(),
			pageBook,
			getWidgetFactory()
		);
		registerSubPane(this.typeConverterComposite);

		installPaneSwitcher(pageBook);
	}

	private AddRemoveListPane<EclipseLinkConverterHolder> addListPane(Composite container) {

		return new AddRemoveListPane<EclipseLinkConverterHolder>(
			this,
			container,
			buildConvertersAdapter(),
			buildDisplayableConvertersListHolder(),
			this.selectedConverterHolder,
			buildConvertersListLabelProvider(),
			null//JpaHelpContextIds.MAPPING_NAMED_QUERIES
		) {
			//TODO yeah, this is weird, but i don't want this to be disabled just
			//because the subject is null. i have no need for that and that is
			//currently how AddRemovePane works.  See OrmQueriesComposite where
			//the work around there is yet another pane enabler.  I want to change
			//how this works in 2.2
			@Override
			public void enableWidgets(boolean enabled) {
				super.enableWidgets(true);
			}
		};
	}

	private void installPaneSwitcher(PageBook pageBook) {
		new ControlSwitcher(this.selectedConverterHolder, buildPaneTransformer(), pageBook);
	}
	
	private Adapter buildConvertersAdapter() {

		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addConverter();
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.CUSTOM_CONVERTER) {
						getSubject().removeCustomConverter((EclipseLinkCustomConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
						getSubject().removeObjectTypeConverter((EclipseLinkObjectTypeConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.STRUCT_CONVERTER) {
						getSubject().removeStructConverter((EclipseLinkStructConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkConverter.TYPE_CONVERTER) {
						getSubject().removeTypeConverter((EclipseLinkTypeConverter) item);
					}
				}
			}
		};
	}

	private void addConverter() {
		addEclipseLinkConverterFromDialog(buildEclipseLinkConverterDialog());
	}
	
	protected EclipseLinkConverterDialog buildEclipseLinkConverterDialog() {
		return new EclipseLinkConverterDialog(getControl().getShell());
	}

	protected void addEclipseLinkConverterFromDialog(EclipseLinkConverterDialog dialog) {
		if (dialog.open() != Window.OK) {
			return;
		}
		String converterType = dialog.getConverterType();
		EclipseLinkConverter converter;
		if (converterType == EclipseLinkConverter.CUSTOM_CONVERTER) {
			converter = this.getSubject().addCustomConverter(getSubject().customConvertersSize());
		}
		else if (converterType == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
			converter = this.getSubject().addObjectTypeConverter(getSubject().objectTypeConvertersSize());
		}
		else if (converterType == EclipseLinkConverter.STRUCT_CONVERTER) {
			converter = this.getSubject().addStructConverter(getSubject().structConvertersSize());
		}
		else if (converterType == EclipseLinkConverter.TYPE_CONVERTER) {
			converter = this.getSubject().addTypeConverter(getSubject().typeConvertersSize());
		}
		else {
			throw new IllegalArgumentException();
		}
		converter.setName(dialog.getName());
		this.selectedConverterHolder.setValue(converter);//so that it gets selected in the List for the user to edit
	}

	private Transformer<EclipseLinkConverter, Control> buildPaneTransformer() {
		return new Transformer<EclipseLinkConverter, Control>() {
			public Control transform(EclipseLinkConverter converter) {
				if (converter == null) {
					return null;
				}

				if (converter.getType() == EclipseLinkConverter.CUSTOM_CONVERTER) {
					return OrmEclipseLinkConvertersComposite.this.converterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
					return OrmEclipseLinkConvertersComposite.this.objectTypeConverterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkConverter.STRUCT_CONVERTER) {
					return OrmEclipseLinkConvertersComposite.this.structConverterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkConverter.TYPE_CONVERTER) {
					return OrmEclipseLinkConvertersComposite.this.typeConverterComposite.getControl();
				}

				return null;
			}
		};
	}

	private ListValueModel<EclipseLinkConverter> buildDisplayableConvertersListHolder() {
		return new ItemPropertyListValueModelAdapter<EclipseLinkConverter>(
			buildEclipseLinkConvertersHolder(),
			EclipseLinkConverter.NAME_PROPERTY
		);
	}

	private ListValueModel<EclipseLinkConverter> buildEclipseLinkConvertersHolder() {
		List<ListValueModel<? extends EclipseLinkConverter>> list = new ArrayList<ListValueModel<? extends EclipseLinkConverter>>();
		list.add(buildCustomConvertersListHolder());
		list.add(buildObjectTypeConvertersListHolder());
		list.add(buildStructConvertersListHolder());
		list.add(buildTypeConvertersListHolder());
		return new CompositeListValueModel<ListValueModel<? extends EclipseLinkConverter>, EclipseLinkConverter>(list);
	}

	private ListValueModel<EclipseLinkCustomConverter> buildCustomConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterHolder, EclipseLinkCustomConverter>(
			getSubjectHolder(),
			EclipseLinkConverterHolder.CUSTOM_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<EclipseLinkCustomConverter> listIterator_() {
				return this.subject.customConverters();
			}

			@Override
			protected int size_() {
				return this.subject.customConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterHolder, EclipseLinkObjectTypeConverter>(
			getSubjectHolder(),
			EclipseLinkConverterHolder.OBJECT_TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<EclipseLinkObjectTypeConverter> listIterator_() {
				return this.subject.objectTypeConverters();
			}

			@Override
			protected int size_() {
				return this.subject.objectTypeConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkStructConverter> buildStructConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterHolder, EclipseLinkStructConverter>(
			getSubjectHolder(),
			EclipseLinkConverterHolder.STRUCT_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<EclipseLinkStructConverter> listIterator_() {
				return this.subject.structConverters();
			}

			@Override
			protected int size_() {
				return this.subject.structConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkTypeConverter> buildTypeConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterHolder, EclipseLinkTypeConverter>(
			getSubjectHolder(),
			EclipseLinkConverterHolder.TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterator<EclipseLinkTypeConverter> listIterator_() {
				return this.subject.typeConverters();
			}

			@Override
			protected int size_() {
				return this.subject.typeConvertersSize();
			}
		};
	}

	private PropertyValueModel<EclipseLinkCustomConverter> buildCustomConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkCustomConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkCustomConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.CUSTOM_CONVERTER ? (EclipseLinkCustomConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkObjectTypeConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkObjectTypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER ? (EclipseLinkObjectTypeConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkStructConverter> buildStructConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkStructConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkStructConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.STRUCT_CONVERTER ? (EclipseLinkStructConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkTypeConverter> buildTypeConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkTypeConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkTypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkConverter.TYPE_CONVERTER ? (EclipseLinkTypeConverter) value : null;
			}
		};
	}

	private ILabelProvider buildConvertersListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return ((EclipseLinkConverter) element).getName();
			}
		};
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.listPane.enableWidgets(enabled);
	}

	//TODO need to check the converter repository for this, should check all converters, except for the override case, hmm
	//we at least need to check typeconverters, converters, structconverters, and objectypeconverters, on this particular
	//object.  or we need to give a warning about the case where you are overriding or an error if it's not an override?
	private Iterator<String> converterNames() {
		return new TransformationIterator<EclipseLinkCustomConverter, String>(getSubject().customConverters()) {
			@Override
			protected String transform(EclipseLinkCustomConverter next) {
				return next.getName();
			}
		};
	}
}