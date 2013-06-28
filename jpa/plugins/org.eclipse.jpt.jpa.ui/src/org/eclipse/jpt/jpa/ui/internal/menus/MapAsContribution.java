/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.menus;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ComparatorAdapter;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.ui.ISources;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;
import com.ibm.icu.text.Collator;

/**
 * This menu contribution is responsible to populate the Map As menu with the
 * registered mapping types defined in the <code>JptPlatformUi</code>.
 * 
 * @see JpaPlatform
 * @see JpaPlatformUi
 */
public abstract class MapAsContribution<T extends JpaStructureNode>
	extends CompoundContributionItem
	implements IWorkbenchContribution
{
	private IServiceLocator serviceLocator;


	public MapAsContribution() {
		super();
	}

	public void initialize(IServiceLocator locator) {
		this.serviceLocator = locator;
	}

	@Override
	protected IContributionItem[] getContributionItems() {
		return ArrayTools.array(this.getContributionItems_(), IContributionItem.class);
	}

	protected Iterable<IContributionItem> getContributionItems_() {
		return new TransformationIterable<MappingUiDefinition, IContributionItem>(
				this.getMappingUiDefinitions(),
				new MappingUiDefinitionTransformer()
			);
	}

	/**
	 * Assume all the selected elements are in the same project (which is a
	 * safe assumption) and return the mapping UI definitions for just the
	 * first item.
	 */
	protected Iterable<MappingUiDefinition> getMappingUiDefinitions() {
		return this.getMappingUiDefinitions(this.getFirstSelectedElement());
	}

	@SuppressWarnings("unchecked")
	protected T getFirstSelectedElement() {
		return (T) this.getSelection().getFirstElement();
	}

	protected IStructuredSelection getSelection() {
		return (IStructuredSelection) this.getHandlerService().getCurrentState().getVariable(ISources.ACTIVE_MENU_SELECTION_NAME);
	}

	protected IHandlerService getHandlerService() {
		return (IHandlerService) this.serviceLocator.getService(IHandlerService.class);
	}

	/**
	 * Return the {@link MappingUiDefinition}s for the specified node
	 * registered with its {@link JpaPlatformUi}.
	 */
	protected Iterable<MappingUiDefinition> getMappingUiDefinitions(final T node) {
		JpaPlatformUi jpaPlatformUi = (JpaPlatformUi) node.getJpaPlatform().getAdapter(JpaPlatformUi.class);
		Iterable<MappingUiDefinition> defs = (jpaPlatformUi != null) ? this.getSortedMappingUiDefinitions(jpaPlatformUi, node) : IterableTools.<MappingUiDefinition>emptyIterable();
		DefaultMappingUiDefinition defaultDef = (jpaPlatformUi != null) ? this.getDefaultMappingUiDefinition(jpaPlatformUi, node) : null;
		return (defaultDef == null) ? defs : IterableTools.insert(defaultDef, defs);
	}

	protected Iterable<MappingUiDefinition> getSortedMappingUiDefinitions(JpaPlatformUi jpaPlatformUi, T node) {
		return IterableTools.sort(this.getMappingUiDefinitions(jpaPlatformUi, node), this.getDefinitionsComparator());
	}

	/**
	 * Return the mapping UI definitions registered with the specified JPA
	 * platform UI for the specified resource type.
	 */
	protected abstract Iterable<MappingUiDefinition> getMappingUiDefinitions(JpaPlatformUi platformUi, T node);

	protected Comparator<MappingUiDefinition> getDefinitionsComparator() {
		return MAPPING_UI_DEFINITION_COMPARATOR;
	}
	protected static final Comparator<MappingUiDefinition> MAPPING_UI_DEFINITION_COMPARATOR = new MappingUiDefinitionComparator();
	protected static class MappingUiDefinitionComparator
		extends ComparatorAdapter<MappingUiDefinition>
	{
		@Override
		public int compare(MappingUiDefinition def1, MappingUiDefinition def2) {
			return Collator.getInstance().compare(def1.getLabel(), def2.getLabel());
		}
	}

	/**
	 * Return the default mapping definition for the specified JPA platform UI
	 * and node.
	 */
	protected abstract DefaultMappingUiDefinition getDefaultMappingUiDefinition(JpaPlatformUi platformUi, T node);

	protected class MappingUiDefinitionTransformer
		extends TransformerAdapter<MappingUiDefinition, IContributionItem>
	{
		@Override
		public IContributionItem transform(MappingUiDefinition def) {
			return MapAsContribution.this.buildContributionItem(def);
		}
	}

	protected IContributionItem buildContributionItem(MappingUiDefinition mappingUiProvider) {
		return new CommandContributionItem(this.buildContributionItemParameter(mappingUiProvider));
	}

	protected CommandContributionItemParameter buildContributionItemParameter(MappingUiDefinition mappingUiDefinition) {
		CommandContributionItemParameter parameter =
			new CommandContributionItemParameter(
				this.serviceLocator,
				this.buildCommandContributionItemParameterID(mappingUiDefinition),
				this.getCommandID(),
				CommandContributionItem.STYLE_CHECK
			);
		parameter.label = mappingUiDefinition.getLabel();
		Map<String, String> parameters = new HashMap<String, String>(1);
		parameters.put(this.getCommandParameterID(), mappingUiDefinition.getKey());
		parameter.parameters = parameters;
		parameter.icon = mappingUiDefinition.getImageDescriptor();
		parameter.visibleEnabled = true;
		return parameter;
	}

	/**
	 * Return a command ID for a {@link CommandContributionItemParameter}
	 * in the form of
	 * <code>"&lt;<em>command ID</em>&gt;.&lt;<em>mapping key</em>&gt;"</code>
	 * (e.g. <code>"org.eclipse.jpt.jpa.core.ui.persistentTypeMapAs.entity"</code>)
	 */
	protected String buildCommandContributionItemParameterID(MappingUiDefinition mappingUiDefinition) {
		return this.getCommandID() + '.' + mappingUiDefinition.getKey();
	}

	/**
	 * Return the unique identifier of the command that will be invoked for
	 * changing the mapping type of the selected nodes.
	 */
	protected abstract String getCommandID();

	/**
	 * Return the unique identifier of the mapping key command parameter that
	 * will be used for the new mapping type of the selected nodes.
	 */
	protected abstract String getCommandParameterID();
}
