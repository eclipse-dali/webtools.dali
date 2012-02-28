/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm.EclipseLinkConverterDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * This pane shows the list of custom converters, object type converters,
 * struct converters, and type converters.
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
 * @version 3.2
 * @since 2.1
 */
public class EclipseLinkConvertersComposite extends Pane<EclipseLinkConverterContainer>
{
	private AddRemoveListPane<EclipseLinkConverterContainer> listPane;
	private EclipseLinkCustomConverterComposite converterComposite;
	private EclipseLinkObjectTypeConverterComposite objectTypeConverterComposite;
	private EclipseLinkStructConverterComposite structConverterComposite;
	private EclipseLinkTypeConverterComposite typeConverterComposite;
	private ModifiablePropertyValueModel<EclipseLinkConverter> selectedConverterHolder;

	public EclipseLinkConvertersComposite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkConverterContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent, false);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedConverterHolder = buildSelectedConverterHolder();
	}

	private ModifiablePropertyValueModel<EclipseLinkConverter> buildSelectedConverterHolder() {
		return new SimplePropertyValueModel<EclipseLinkConverter>();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// List pane
		this.listPane = addListPane(container);
		this.installPaneEnabler();

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

	private AddRemoveListPane<EclipseLinkConverterContainer> addListPane(Composite container) {

		return new AddRemoveListPane<EclipseLinkConverterContainer>(
			this,
			container,
			buildConvertersAdapter(),
			buildDisplayableConvertersListHolder(),
			this.selectedConverterHolder,
			buildConvertersListLabelProvider(),
			null
		);
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
					if (((EclipseLinkConverter) item).getType() == EclipseLinkCustomConverter.class) {
						getSubject().removeCustomConverter((EclipseLinkCustomConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkObjectTypeConverter.class) {
						getSubject().removeObjectTypeConverter((EclipseLinkObjectTypeConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkStructConverter.class) {
						getSubject().removeStructConverter((EclipseLinkStructConverter) item);
					}
					else if (((EclipseLinkConverter) item).getType() == EclipseLinkTypeConverter.class) {
						getSubject().removeTypeConverter((EclipseLinkTypeConverter) item);
					}
				}
			}
		};
	}

	private void addConverter() {
		this.addEclipseLinkConverterFromDialog(this.buildEclipseLinkConverterDialog());
	}

	protected EclipseLinkConverterDialog buildEclipseLinkConverterDialog() {
		return new EclipseLinkConverterDialog(this.getShell(), this.getSubject());
	}

	protected void addEclipseLinkConverterFromDialog(EclipseLinkConverterDialog dialog) {
		if (dialog.open() != Window.OK) {
			return;
		}
		Class<? extends EclipseLinkConverter> converterType = dialog.getConverterType();
		EclipseLinkConverter converter;
		if (converterType == EclipseLinkCustomConverter.class) {
			converter = this.getSubject().addCustomConverter(getSubject().getCustomConvertersSize());
		}
		else if (converterType == EclipseLinkObjectTypeConverter.class) {
			converter = this.getSubject().addObjectTypeConverter(getSubject().getObjectTypeConvertersSize());
		}
		else if (converterType == EclipseLinkStructConverter.class) {
			converter = this.getSubject().addStructConverter(getSubject().getStructConvertersSize());
		}
		else if (converterType == EclipseLinkTypeConverter.class) {
			converter = this.getSubject().addTypeConverter(getSubject().getTypeConvertersSize());
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

				if (converter.getType() == EclipseLinkCustomConverter.class) {
					return EclipseLinkConvertersComposite.this.converterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkObjectTypeConverter.class) {
					return EclipseLinkConvertersComposite.this.objectTypeConverterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkStructConverter.class) {
					return EclipseLinkConvertersComposite.this.structConverterComposite.getControl();
				}
				if (converter.getType() == EclipseLinkTypeConverter.class) {
					return EclipseLinkConvertersComposite.this.typeConverterComposite.getControl();
				}

				return null;
			}
		};
	}

	private ListValueModel<EclipseLinkConverter> buildDisplayableConvertersListHolder() {
		return new ItemPropertyListValueModelAdapter<EclipseLinkConverter>(
			buildEclipseLinkConvertersHolder(),
			JpaNamedContextNode.NAME_PROPERTY
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
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkCustomConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.CUSTOM_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkCustomConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkCustomConverter>(this.subject.getCustomConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getCustomConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkObjectTypeConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.OBJECT_TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkObjectTypeConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkObjectTypeConverter>(this.subject.getObjectTypeConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getObjectTypeConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkStructConverter> buildStructConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkStructConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.STRUCT_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkStructConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkStructConverter>(this.subject.getStructConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getStructConvertersSize();
			}
		};
	}

	private ListValueModel<EclipseLinkTypeConverter> buildTypeConvertersListHolder() {
		return new ListAspectAdapter<EclipseLinkConverterContainer, EclipseLinkTypeConverter>(
			getSubjectHolder(),
			EclipseLinkConverterContainer.TYPE_CONVERTERS_LIST)
		{
			@Override
			protected ListIterable<EclipseLinkTypeConverter> getListIterable() {
				return new SuperListIterableWrapper<EclipseLinkTypeConverter>(this.subject.getTypeConverters());
			}

			@Override
			protected int size_() {
				return this.subject.getTypeConvertersSize();
			}
		};
	}

	private PropertyValueModel<EclipseLinkCustomConverter> buildCustomConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkCustomConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkCustomConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkCustomConverter.class ? (EclipseLinkCustomConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkObjectTypeConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkObjectTypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkObjectTypeConverter.class ? (EclipseLinkObjectTypeConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkStructConverter> buildStructConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkStructConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkStructConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkStructConverter.class ? (EclipseLinkStructConverter) value : null;
			}
		};
	}

	private PropertyValueModel<EclipseLinkTypeConverter> buildTypeConverterHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkTypeConverter>(this.selectedConverterHolder) {
			@Override
			protected EclipseLinkTypeConverter transform_(EclipseLinkConverter value) {
				return value.getType() == EclipseLinkTypeConverter.class ? (EclipseLinkTypeConverter) value : null;
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

	private void installPaneEnabler() {
		new PaneEnabler(
			this.buildPaneEnablerHolder(),
			this.listPane
		);
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConverterContainer, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(EclipseLinkConverterContainer value) {
				return (value != null);
			}
		};
	}
}