/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.RelationshipReference;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * Abstract superclass for joining strategy form panes
 * 
 * Here is the basic layout of this pane:  
 * <pre>
 * -----------------------------------------------------------------------------
 * | o <label> _______________________________________________________________ |
 * | |                                                                       | |
 * | |          (joining strategy details composite)                         | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link OwnableRelationshipReference}
 * @see {@link MappedByJoiningStrategy}
 * @see {@link OneToOneJoiningStrategyPane}
 *
 * @version 3.0
 * @since 2.1
 */
public abstract class AbstractJoiningStrategyPane
		<R extends RelationshipReference, S extends JoiningStrategy> 
	extends Pane<R>
{
	protected WritablePropertyValueModel<Boolean> usesStrategyHolder;
	
	protected PropertyValueModel<S> joiningStrategyHolder;
	
	protected Composite strategyDetailsComposite;
	
	
	/**
	 * Creates a new <code>AbstractJoiningStrategyPane</code>.
	 *
	 * @param parentPane The parent form pane
	 * @param parent The parent container
	 */
	protected AbstractJoiningStrategyPane(
			Pane<? extends R> parentPane, 
			Composite parent) {
		super(parentPane, parent);
	}
	
	protected AbstractJoiningStrategyPane(Pane<?> parentPane, 
		PropertyValueModel<? extends R> subjectHolder,
        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		this.usesStrategyHolder = buildUsesStrategyHolder();
		this.joiningStrategyHolder = buildJoiningStrategyHolder();
	}
	
	protected abstract WritablePropertyValueModel<Boolean> buildUsesStrategyHolder();
	
	protected abstract PropertyValueModel<S> buildJoiningStrategyHolder();
	
	@Override
	protected void initializeLayout(Composite container) {
		addRadioButton(
			container,
			getStrategyLabelKey(),
			this.usesStrategyHolder,
			null);
		
		PageBook pageBook = new PageBook(container, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		pageBook.setLayoutData(gd);
		
		this.strategyDetailsComposite = buildStrategyDetailsComposite(pageBook);
		
		new ControlSwitcher(this.usesStrategyHolder, buildPageBookTransformer(), pageBook);
	}
	
	protected abstract String getStrategyLabelKey();
	
	protected abstract Composite buildStrategyDetailsComposite(Composite parent);
	
	protected Transformer<Boolean, Control> buildPageBookTransformer() {
		return new Transformer<Boolean, Control>() {
			public Control transform(Boolean usesStrategy) {
				return (usesStrategy.booleanValue()) ? 
					AbstractJoiningStrategyPane.this.strategyDetailsComposite :
					null;
			}
		};
	}
}
