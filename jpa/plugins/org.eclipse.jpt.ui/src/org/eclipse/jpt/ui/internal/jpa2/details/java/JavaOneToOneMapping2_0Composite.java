package org.eclipse.jpt.ui.internal.jpa2.details.java;

import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.CascadeComposite;
import org.eclipse.jpt.ui.internal.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.details.OneToOneJoiningStrategyPane;
import org.eclipse.jpt.ui.internal.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.details.TargetEntityComposite;
import org.eclipse.jpt.ui.internal.jpa2.details.AbstractOneToOneMapping2_0Composite;
import org.eclipse.jpt.ui.internal.jpa2.details.DerivedId2_0Pane;
import org.eclipse.jpt.ui.internal.jpa2.details.OrphanRemoval2_0Composite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TargetEntityComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | DerivedId2_0Pane                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OneToOneJoiningStrategyPane                                           | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | CascadeComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OrphanRemoval2_0Composite                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link JavaOneToOneMapping2_0}
 * @see {@link TargetEntityComposite}
 * @see {@link DerivedId2_0Pane}
 * @see {@link OneToOneJoiningStrategyPane}
 * @see {@link FetchTypeComposite}
 * @see {@link OptionalComposite}
 * @see {@link CascadeComposite}
 * @see {@link OrphanRemoval2_0Composite}
 */
public class JavaOneToOneMapping2_0Composite<T extends JavaOneToOneMapping2_0>
	extends AbstractOneToOneMapping2_0Composite<T>
{
	public JavaOneToOneMapping2_0Composite(
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();
		
		new TargetEntityComposite(this, this.addPane(container, groupBoxMargin));
		new DerivedId2_0Pane(this, this.buildDerivedIdHolder(), this.addPane(container, groupBoxMargin));
		new OneToOneJoiningStrategyPane(this, this.buildJoiningHolder(), container);
		new FetchTypeComposite(this, this.addPane(container, groupBoxMargin));
		new OptionalComposite(this, this.addPane(container, groupBoxMargin));
		new CascadeComposite(this, this.buildCascadeHolder(),  this.addSubPane(container, 5));
		new OrphanRemoval2_0Composite(this, this.addPane(container, groupBoxMargin));
	}
}
