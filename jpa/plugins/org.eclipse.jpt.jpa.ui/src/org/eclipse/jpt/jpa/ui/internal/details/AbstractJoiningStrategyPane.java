/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.MappedByRelationship;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.SpecifiedMappedByRelationshipStrategy;
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
 * @see MappedByRelationship
 * @see SpecifiedMappedByRelationshipStrategy
 * @see OneToOneJoiningStrategyPane
 *
 * @version 2.3
 * @since 2.1
 */
public abstract class AbstractJoiningStrategyPane<R extends Relationship> 
	extends Pane<R>
{
	protected Control strategyDetailsComposite;
	
	
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
	
	protected abstract ModifiablePropertyValueModel<Boolean> buildUsesStrategyModel();
	

	@Override
	protected Composite addComposite(Composite container) {
		PageBook pageBook = new PageBook(container, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		pageBook.setLayoutData(gd);
		return pageBook;
	}

	@Override
	public Composite getControl() {
		return (Composite) super.getControl();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.strategyDetailsComposite = this.buildStrategyDetailsComposite(container);
		// the container is a PageBook, it was built in addComposite(Composite)
		SWTBindingTools.bind(this.buildUsesStrategyModel(), buildPageBookTransformer(container), (PageBook) container);
	}
	
	protected Control getStrategyDetailsComposite(Composite parent) {
		if (this.strategyDetailsComposite == null) {
			this.strategyDetailsComposite = this.buildStrategyDetailsComposite(parent);
		}
		return this.strategyDetailsComposite;
	}

	protected abstract Control buildStrategyDetailsComposite(Composite parent);
	
	protected Transformer<Boolean, Control> buildPageBookTransformer(Composite parent) {
		return new PageBookTransformer(parent);
	}

	protected class PageBookTransformer
		extends TransformerAdapter<Boolean, Control>
	{
		private final Composite parent;

		protected PageBookTransformer(Composite parent) {
			this.parent = parent;
		}

		@Override
		public Control transform(Boolean usesStrategy) {
			return (usesStrategy.booleanValue()) ? 
				AbstractJoiningStrategyPane.this.getStrategyDetailsComposite(this.parent) :
				null;
		}
	}
}
