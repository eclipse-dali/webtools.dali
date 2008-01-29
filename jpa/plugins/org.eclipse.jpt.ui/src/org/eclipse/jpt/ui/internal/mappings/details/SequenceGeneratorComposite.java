/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.ISequenceGenerator;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.AbstractDatabaseObjectCombo;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                     ----------------------------------------------------- |
 * | Name:               | I                                                 | |
 * |                     ----------------------------------------------------- |
 * |                     ----------------------------------------------------- |
 * | Sequence Generator: | I                                               |v| |
 * |                     ----------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IIdMapping
 * @see ISequenceGenerator
 * @see GenerationComposite - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class SequenceGeneratorComposite extends GeneratorComposite<ISequenceGenerator>
{
	private AbstractDatabaseObjectCombo<IIdMapping> sequenceNameCombo;
	private PropertyChangeListener sequenceNamePropertyChangeListener;

	/**
	 * Creates a new <code>SequenceGeneratorComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public SequenceGeneratorComposite(AbstractFormPane<? extends IIdMapping> parentPane,
	                                  Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ISequenceGenerator buildGenerator() {
		return subject().addSequenceGenerator();
	}

	private AbstractDatabaseObjectCombo<IIdMapping> buildSequenceNameCombo(Composite parent) {
		return new AbstractDatabaseObjectCombo<IIdMapping>(this, parent) {
			@Override
			protected void initializeLayout(Composite container) {
				super.initializeLayout(container);
				getCombo().add(JptUiMappingsMessages.SequenceGeneratorComposite_default);
				getCombo().addModifyListener(buildSequenceNameListener());
			}

			@Override
			protected void schemaChanged(Schema schema) {
			}

			@Override
			protected void setValue(String value) {
				// TODO
			}

			@Override
			protected void tableChanged(Table table) {
			}

			@Override
			protected String value() {
				return ""; // TODO
			}
		};
	}

	private ModifyListener buildSequenceNameListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}

				String text = ((CCombo) e.getSource()).getText();

				if (text != null && getSequenceCombo().getItemCount() > 0 && text.equals(getSequenceCombo().getItem(0))) {
					text = null;
				}

				ISequenceGenerator generator = getGenerator();

				if (generator == null) {
					generator = buildGenerator();
				}

				generator.setSpecifiedSequenceName(text);
			}
		};
	}

	private PropertyChangeListener buildSequenceNamePropertyChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (isPopulating()) {
					return;
				}

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						setPopulating(true);
						try {
							SequenceGeneratorComposite.this.populateSequenceNameCombo();
						}
						finally {
							setPopulating(false);
						}
					}
				});
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void clear() {
		super.clear();
		this.getSequenceCombo().select(0);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.populateSequenceNameCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected ISequenceGenerator getGenerator() {
		return (subject() != null) ? subject().getSequenceGenerator() : null;
	}

	private CCombo getSequenceCombo() {
		return this.sequenceNameCombo.getCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

		sequenceNamePropertyChangeListener = buildSequenceNamePropertyChangeListener();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		Text nameText = this.buildNameText(shell());
		this.setNameText(nameText);

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_name,
			nameText,
			IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_NAME
		);

		// Sequence Generator widgets
		this.sequenceNameCombo = this.buildSequenceNameCombo(shell());

		this.buildLabeledComposite(
			container,
			JptUiMappingsMessages.SequenceGeneratorComposite_sequence,
			this.sequenceNameCombo.getCombo().getParent(),
			IJpaHelpContextIds.MAPPING_SEQUENCE_GENERATOR_SEQUENCE
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void installListeners(ISequenceGenerator generator) {
		super.installListeners(generator);

		if (generator != null) {
			generator.addPropertyChangeListener(PropertyValueModel.VALUE, sequenceNamePropertyChangeListener);;
		}
	}

	private void populateSequenceNameCombo() {

		this.getSequenceCombo().removeAll();//(1, this.getSequenceCombo().getItemCount());

//		if (this.getConnectionProfile().isConnected()) {
//			this.sequenceNameCombo.remove(1, this.sequenceNameCombo.getItemCount() - 1);
//			Schema schema = getConnectionProfile().getDatabase().schemaNamed(getGenerator().getJpaProject().getSchemaName());
//			if (schema != null) {
//				for (Iterator stream = CollectionTools.sort(schema.sequenceNames()); stream.hasNext();) {
//					this.sequenceNameCombo.add((String) stream.next());
//				}
//			}
//		}
		if (this.getGenerator() != null) {
			String sequenceName = this.getGenerator().getSpecifiedSequenceName();

			if (sequenceName != null) {
				if (!this.getSequenceCombo().getText().equals(sequenceName)) {
					this.getSequenceCombo().setText(sequenceName);
				}
			}
			else {
				this.getSequenceCombo().select(0);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String propertyName() {
		return IIdMapping.SEQUENCE_GENERATOR_PROPERTY;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void uninstallListeners(ISequenceGenerator generator) {
		super.uninstallListeners(generator);

		if (generator != null) {
			generator.removePropertyChangeListener(PropertyValueModel.VALUE, sequenceNamePropertyChangeListener);;
		}
	}
}