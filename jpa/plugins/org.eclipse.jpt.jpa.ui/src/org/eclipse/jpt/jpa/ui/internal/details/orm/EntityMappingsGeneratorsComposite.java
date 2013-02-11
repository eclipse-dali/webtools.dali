/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.core.context.SequenceGenerator;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.jpt.jpa.ui.internal.details.GeneratorComposite;
import org.eclipse.jpt.jpa.ui.internal.details.GeneratorComposite.GeneratorBuilder;
import org.eclipse.jpt.jpa.ui.internal.details.SequenceGeneratorComposite;
import org.eclipse.jpt.jpa.ui.internal.details.TableGeneratorComposite;
import org.eclipse.osgi.util.NLS;
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
 * | | OrmSequenceGeneratorComposite or OrmTableGeneratorComposite           | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EntityMappings
 * @see OrmGenerator
 * @see OrmSequenceGenerator
 * @see OrmTableGenerator
 * @see EntityComposite - The parent container
 * @see OrmSequenceGeneratorComposite
 * @see OrmTableGeneratorComposite
 *
 * @version 2.2
 * @since 2.0
 */
public class EntityMappingsGeneratorsComposite extends Pane<EntityMappings>
{
	GeneratorComposite<SequenceGenerator> sequenceGeneratorPane;
	TableGeneratorComposite tableGeneratorPane;
	ModifiableCollectionValueModel<OrmGenerator> selectedGeneratorsModel;
	PropertyValueModel<OrmGenerator> selectedGeneratorModel;

	/**
	 * Creates a new <code>EntityMappingsGeneratorsComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EntityMappingsGeneratorsComposite(
						Pane<? extends EntityMappings> parentPane,
						Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedGeneratorsModel = this.buildSelectedGeneratorsModel();
		this.selectedGeneratorModel = this.buildSelectedGeneratorModel(this.selectedGeneratorsModel);
	}

	private ModifiableCollectionValueModel<OrmGenerator> buildSelectedGeneratorsModel() {
		return new SimpleCollectionValueModel<OrmGenerator>();
	}

	protected PropertyValueModel<OrmGenerator> buildSelectedGeneratorModel(ModifiableCollectionValueModel<OrmGenerator> selectedGeneratorsModel) {
		return new CollectionPropertyValueModelAdapter<OrmGenerator, OrmGenerator>(selectedGeneratorsModel) {
			@Override
			protected OrmGenerator buildValue() {
				if (this.collectionModel.size() == 1) {
					return this.collectionModel.iterator().next();
				}
				return null;
			}
		};
	}
	
	OrmGenerator addGenerator() {
		return addGeneratorFromDialog(buildAddGeneratorDialog());
	}
	
	protected AddGeneratorDialog buildAddGeneratorDialog() {
		return new AddGeneratorDialog(this.getShell(), this.getResourceManager(), this.getSubject().getPersistenceUnit());
	}

	protected OrmGenerator addGeneratorFromDialog(AddGeneratorDialog dialog) {
		if (dialog.open() != Window.OK) {
			return null;
		}
		String generatorType = dialog.getGeneratorType();
		OrmGenerator generator;
		if (generatorType == AddGeneratorDialog.TABLE_GENERATOR) {
			generator = this.getSubject().addTableGenerator();
		}
		else if (generatorType == AddGeneratorDialog.SEQUENCE_GENERATOR) {
			generator = this.getSubject().addSequenceGenerator();
		}
		else {
			throw new IllegalArgumentException();
		}
		generator.setName(dialog.getName());
		return generator;
	}

	private ListValueModel<OrmGenerator> buildDisplayableGeneratorListHolder() {
		return new ItemPropertyListValueModelAdapter<OrmGenerator>(
			buildGeneratorsListHolder(),
			JpaNamedContextNode.NAME_PROPERTY
		);
	}

	private Adapter<OrmGenerator> buildGeneratorAdapter() {

		return new AddRemoveListPane.AbstractAdapter<OrmGenerator>() {
			public OrmGenerator addNewItem() {
				return addGenerator();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<OrmGenerator> selectedItemsModel) {
				return buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<OrmGenerator> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				OrmGenerator item = selectedItemsModel.iterator().next();
				if (item instanceof OrmSequenceGenerator) {
					getSubject().removeSequenceGenerator((OrmSequenceGenerator) item);
				}
				else {
					getSubject().removeTableGenerator((OrmTableGenerator) item);
				}
			}
		};
	}

	private ILabelProvider buildGeneratorLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				OrmGenerator generator = (OrmGenerator) element;
				String name = generator.getName();

				if (name == null) {
					int index = -1;

					if (generator instanceof OrmSequenceGenerator) {
						index = IterableTools.indexOf(getSubject().getSequenceGenerators(), generator);
					}
					else {
						index = IterableTools.indexOf(getSubject().getTableGenerators(), generator);
					}

					name = NLS.bind(JptJpaUiDetailsOrmMessages.ORM_GENERATORS_COMPOSITE_DISPLAY_STRING, Integer.valueOf(index));
				}

				return name;
			}
		};
	}

	private ListValueModel<OrmGenerator> buildGeneratorsListHolder() {
		List<ListValueModel<? extends OrmGenerator>> list = new ArrayList<ListValueModel<? extends OrmGenerator>>();
		list.add(buildSequenceGeneratorListHolder());
		list.add(buildTableGeneratorListHolder());
		return CompositeListValueModel.forModels(list);
	}

	private Transformer<OrmGenerator, Control> buildPaneTransformer(final Composite container) {
		return new Transformer<OrmGenerator, Control>() {
			public Control transform(OrmGenerator generator) {

				if (generator == null) {
					return null;
				}

				if (generator instanceof OrmSequenceGenerator) {
					return EntityMappingsGeneratorsComposite.this.getSequenceGeneratorComposite(container).getControl();
				}

				return EntityMappingsGeneratorsComposite.this.getTableGeneratorComposite(container).getControl();
			}
		};
	}

	private PropertyValueModel<SequenceGenerator> buildSequenceGeneratorHolder() {
		return new TransformationPropertyValueModel<OrmGenerator, SequenceGenerator>(this.selectedGeneratorModel) {
			@Override
			protected SequenceGenerator transform_(OrmGenerator value) {
				return (value instanceof SequenceGenerator) ? (SequenceGenerator) value : null;
			}
		};
	}

	private ListValueModel<OrmSequenceGenerator> buildSequenceGeneratorListHolder() {
		return new ListAspectAdapter<EntityMappings, OrmSequenceGenerator>(
			getSubjectHolder(),
			EntityMappings.SEQUENCE_GENERATORS_LIST)
		{
			@Override
			protected ListIterable<OrmSequenceGenerator> getListIterable() {
				return this.subject.getSequenceGenerators();
			}
			@Override
			protected int size_() {
				return this.subject.getSequenceGeneratorsSize();
			}
		};
	}

	private PropertyValueModel<TableGenerator> buildTableGeneratorHolder() {
		return new TransformationPropertyValueModel<OrmGenerator, TableGenerator>(this.selectedGeneratorModel) {
			@Override
			protected TableGenerator transform_(OrmGenerator value) {
				return (value instanceof TableGenerator) ? (TableGenerator) value : null;
			}
		};
	}

	private ListValueModel<OrmTableGenerator> buildTableGeneratorListHolder() {
		return new ListAspectAdapter<EntityMappings, OrmTableGenerator>(
			getSubjectHolder(),
			EntityMappings.TABLE_GENERATORS_LIST)
		{
			@Override
			protected ListIterable<OrmTableGenerator> getListIterable() {
				return this.subject.getTableGenerators();
			}
			@Override
			protected int size_() {
				return this.subject.getTableGeneratorsSize();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		// List pane
		addListPane(container);

		// Property pane

		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.installPaneSwitcher(pageBook);
	}

	protected GeneratorComposite<SequenceGenerator> getSequenceGeneratorComposite(Composite container) {
		if (this.sequenceGeneratorPane == null) {
			this.sequenceGeneratorPane =
				this.buildSequenceGeneratorComposite(container, 
													this.buildSequenceGeneratorHolder(), 
													this.buildSequenceGeneratorBuilder());
		}
		return this.sequenceGeneratorPane;
	}

	protected GeneratorComposite<SequenceGenerator> buildSequenceGeneratorComposite(
			Composite parent,
			PropertyValueModel<SequenceGenerator> sequenceGeneratorHolder,
			GeneratorBuilder<SequenceGenerator> generatorBuilder) {

		return new SequenceGeneratorComposite(
			this,
			sequenceGeneratorHolder,
			parent,
			generatorBuilder
		);
	}

	protected TableGeneratorComposite getTableGeneratorComposite(Composite container) {
		if (this.tableGeneratorPane == null) {
			this.tableGeneratorPane = 
				new TableGeneratorComposite(this, 
											this.buildTableGeneratorHolder(), 
											container, 
											this.buildTableGeneratorBuilder());
		}
		return this.tableGeneratorPane;
	}

	private AddRemoveListPane<EntityMappings, OrmGenerator> addListPane(Composite container) {

		return new AddRemoveListPane<EntityMappings, OrmGenerator>(
			this,
			container,
			this.buildGeneratorAdapter(),
			this.buildDisplayableGeneratorListHolder(),
			this.selectedGeneratorsModel,
			this.buildGeneratorLabelProvider()
		);
	}

	private void installPaneSwitcher(PageBook pageBook) {
		new ControlSwitcher(this.selectedGeneratorModel, this.buildPaneTransformer(pageBook), pageBook);
	}	

	private GeneratorBuilder<SequenceGenerator> buildSequenceGeneratorBuilder() {
		return new GeneratorBuilder<SequenceGenerator>() {
			public SequenceGenerator addGenerator() {
				throw new UnsupportedOperationException("The sequence generator will never be null so we do not need to implement this"); //$NON-NLS-1$
			}
		};
	}

	private GeneratorBuilder<TableGenerator> buildTableGeneratorBuilder() {
		return new GeneratorBuilder<TableGenerator>() {
			public TableGenerator addGenerator() {
				throw new UnsupportedOperationException("The table generator will never be null so we do not need to implement this"); //$NON-NLS-1$
			}
		};
	}

}
