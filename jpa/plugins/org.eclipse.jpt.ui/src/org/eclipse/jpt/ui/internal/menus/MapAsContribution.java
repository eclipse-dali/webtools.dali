/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.menus;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.internal.jface.ImageImageDescriptor;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
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
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class MapAsContribution<T extends JpaStructureNode>
	extends CompoundContributionItem
	implements IWorkbenchContribution
{
	/**
	 * Keeps track of the <code>IServiceLocator</code> which is used to retrieve
	 * various services required for invoking the <code>PersistentAttributeMapAsHandler</code>.
	 */
	private IServiceLocator serviceLocator;
	
	
	/**
	 * Creates a new <code>PersistentAttributeMapAsContribution</code>.
	 */
	public MapAsContribution() {
		super();
	}
	
	
	public void initialize(IServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}
		
	@Override
	protected IContributionItem[] getContributionItems() {
		// Retrieve the selection from the handler service
		// which should be an IStructuredSelection of JpaStructureNodes
		IHandlerService handlerService = 
			(IHandlerService) this.serviceLocator.getService(IHandlerService.class);
		IStructuredSelection currentSelection = 
			(IStructuredSelection) handlerService.getCurrentState().getVariable(ISources.ACTIVE_CURRENT_SELECTION_NAME);
		
		// Assume that all nodes are in the same project (which is very safe)
		// and retrieve the mapping UI providers just from the first item
		T node = (T) currentSelection.getFirstElement();
		
		return 
			ArrayTools.array(
				new TransformationIterator<MappingUiDefinition<T, ?>, IContributionItem>(mappingUiDefinitions(node)) {
					@Override
					protected IContributionItem transform(MappingUiDefinition<T, ?> next) {
						return createContributionItem(next);
					}
				},
				new IContributionItem[0]);
	}
	

	protected Comparator<MappingUiDefinition<T, ?>> getDefinitionsComparator() {
		return new Comparator<MappingUiDefinition<T, ?>>() {
			public int compare(MappingUiDefinition<T, ?> item1, MappingUiDefinition<T, ?> item2) {
				String displayString1 = item1.getLabel();
				String displayString2 = item2.getLabel();
				return Collator.getInstance().compare(displayString1, displayString2);
			}
		};
	}

	/**
	 * Retrieves the registered {@link MappingUiDefinition}s from the given node, 
	 * using its {@link JpaPlatformUi}.
	 *
	 * @param node A test node to determine the {@link JpaPlatformUi} and type 
	 * of providers to return
	 * @return The list of registered {@link MappingUiDefinition}s
	 */
	protected <U extends T> Iterator<? extends MappingUiDefinition<T, ?>> mappingUiDefinitions(final T node) {
		JpaPlatform jpaPlatform = node.getJpaProject().getJpaPlatform();
		JpaPlatformUi jpaPlatformUi = JptUiPlugin.instance().getJpaPlatformUi(jpaPlatform);
		
		Iterator<? extends MappingUiDefinition<T, ?>> sortedMappingUiDefinitions = 
				CollectionTools.sort(
					new FilteringIterator<MappingUiDefinition<T, ?>>(
							mappingUiDefinitions(jpaPlatformUi, node.getResourceType())) {
						 @Override
						protected boolean accept(MappingUiDefinition<T, ?> o) {
							return o.isEnabledFor(node);
						}
					},
					getDefinitionsComparator());
		
		DefaultMappingUiDefinition<T, ?> defaultDefinition = getDefaultMappingUiDefinition(jpaPlatformUi, node);
		if (defaultDefinition != null) {
			return new CompositeIterator<MappingUiDefinition<T, ?>>(defaultDefinition, sortedMappingUiDefinitions);
		}
		return sortedMappingUiDefinitions;
	}

	/**
	* Retrieves the registered {@link MappingUiDefinition}s from the given 
	* {@link JpaPlatformUi} and {@link JpaStructureNode} (to determine type of 
	* mapping providers to retrieve).
	*
	* @param jpaPlatformUi The active {@link JpaPlatformUi} from where the
	* provider can be retrieved
	* @param node A test node to determine type of providers to return
	* @return The list of registered {@link MappingUiDefinition}s
	*/
	protected abstract Iterator<? extends MappingUiDefinition<T, ?>> 
		mappingUiDefinitions(JpaPlatformUi platformUi, JptResourceType resourceType);
	
	/**
	* Creates the default provider responsible for clearing the mapping type.
	* If not default provider, then return null
	*
	* @return A provider that acts as a default mapping provider
	*/
	protected abstract DefaultMappingUiDefinition<T, ?> getDefaultMappingUiDefinition(JpaPlatformUi platformUi, T node);
			
	protected IContributionItem createContributionItem(MappingUiDefinition<T, ?> mappingUiProvider) {
		return new CommandContributionItem(createParameter(mappingUiProvider));
	}
	
	protected CommandContributionItemParameter createParameter(MappingUiDefinition<T, ?> mappingUiDefinition) {
		CommandContributionItemParameter parameter =
			new CommandContributionItemParameter(
					this.serviceLocator, 
					createCommandContributionItemId(mappingUiDefinition),
					getCommandId(),
					CommandContributionItem.STYLE_CHECK);
		parameter.label = mappingUiDefinition.getLabel();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(getCommandParameterId(), mappingUiDefinition.getKey());
		parameter.parameters = parameters;
		parameter.icon = new ImageImageDescriptor(mappingUiDefinition.getImage());
		parameter.visibleEnabled = true;
		return parameter;
	}
	
	/**
	 * Retrieves the unique identifier of the command that will be invoked for
	 * changing the mapping type of the selected nodes.
	 *
	 * @return The unique identifier of the "map as" command
	 */
	protected abstract String getCommandId();
	
	/**
	 * Retrieves the unique identifier of the mapping key command parameter that 
	 * will be used for the new mapping type of the selected nodes.
	 *
	 * @return The unique identifier of the "map as" command parameter
	 */
	protected abstract String getCommandParameterId();
	
	/**
	 * Returns an id for a {@link CommandContributionItem} in the form of 
	 * "<commandId>.<mappingKey>"  
	 * (for example "org.eclipse.jpt.core.ui.persistentTypeMapAs.entity")
	 */
	protected String createCommandContributionItemId(MappingUiDefinition<T, ?> mappingUiDefinition) {
		return getCommandId() + "." + mappingUiDefinition.getKey();
	}
}
