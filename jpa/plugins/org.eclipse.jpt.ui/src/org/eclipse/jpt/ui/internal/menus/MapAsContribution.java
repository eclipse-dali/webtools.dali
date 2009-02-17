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

import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.DefaultMappingUiProvider;
import org.eclipse.jpt.ui.details.MappingUiProvider;
import org.eclipse.jpt.ui.internal.jface.ImageImageDescriptor;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.ui.ISources;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.IWorkbenchContribution;
import org.eclipse.ui.services.IServiceLocator;

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
public abstract class MapAsContribution extends CompoundContributionItem
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
		JpaStructureNode node = (JpaStructureNode) currentSelection.getFirstElement();
		
		return 
			CollectionTools.array(
				new TransformationIterator<MappingUiProvider<?>, IContributionItem>(mappingUiProviders(node)) {
					@Override
					protected IContributionItem transform(MappingUiProvider<?> next) {
						return createContributionItem(next);
					}
				},
				new IContributionItem[0]);
	}
	

	protected Comparator<MappingUiProvider<?>> getProvidersComparator() {
		return new Comparator<MappingUiProvider<?>>() {
			public int compare(MappingUiProvider<?> item1, MappingUiProvider<?> item2) {
				String displayString1 = item1.getLabel();
				String displayString2 = item2.getLabel();
				return Collator.getInstance().compare(displayString1, displayString2);
			}
		};
	}

	/**
	 * Retrieves the registered {@link MappingUiProvider}s from the given node, 
	 * using its {@link JpaPlatformUi}.
	 *
	 * @param node A test node to determine the {@link JpaPlatformUi} and type 
	 * of providers to return
	 * @return The list of registered {@link MappingUiProvider}s
	 */
	protected Iterator<? extends MappingUiProvider<?>> mappingUiProviders(JpaStructureNode node) {
		JpaPlatform jpaPlatform = node.getJpaProject().getJpaPlatform();
		JpaPlatformUi jpaPlatformUi = JptUiPlugin.instance().getJpaPlatformUi(jpaPlatform);
		
		Iterator<? extends MappingUiProvider<?>> sortedMappingUiProviders = 
			CollectionTools.sort(
				mappingUiProviders(jpaPlatformUi, node.getContentType()), 
				getProvidersComparator());
		
		DefaultMappingUiProvider<?> defaultProvider = getDefaultProvider(jpaPlatformUi, node);
		if (defaultProvider != null) {
			return new CompositeIterator<MappingUiProvider<?>>(defaultProvider, sortedMappingUiProviders);
		}
		return sortedMappingUiProviders;
	}

	/**
	* Retrieves the registered {@link MappingUiProvider}s from the given 
	* {@link JpaPlatformUi} and {@link JpaStructureNode} (to determine type of 
	* mapping providers to retrieve).
	*
	* @param jpaPlatformUi The active {@link JpaPlatformUi} from where the
	* provider can be retrieved
	* @param node A test node to determine type of providers to return
	* @return The list of registered {@link MappingUiProvider}s
	*/
	protected abstract Iterator<? extends MappingUiProvider<?>> 
		mappingUiProviders(JpaPlatformUi platformUi, IContentType contentType);
	
	/**
	* Creates the default provider responsible for clearing the mapping type.
	* If not default provider, then return null
	*
	* @return A provider that acts as a default mapping provider
	*/
	protected abstract DefaultMappingUiProvider<?> getDefaultProvider(JpaPlatformUi platformUi, JpaStructureNode node);
			
	protected IContributionItem createContributionItem(MappingUiProvider<?> mappingUiProvider) {
		return new CommandContributionItem(createParameter(mappingUiProvider));
	}
	
	protected CommandContributionItemParameter createParameter(MappingUiProvider<?> mappingUiProvider) {
		CommandContributionItemParameter parameter =
			new CommandContributionItemParameter(
					serviceLocator, 
					createCommandContributionItemId(mappingUiProvider),
					getCommandId(),
					CommandContributionItem.STYLE_CHECK);
		parameter.label = mappingUiProvider.getLabel();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(getCommandParameterId(), mappingUiProvider.getKey());
		parameter.parameters = parameters;
		parameter.icon = new ImageImageDescriptor(mappingUiProvider.getImage());
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
	protected String createCommandContributionItemId(MappingUiProvider<?> mappingUiProvider) {
		return getCommandId() + "." + mappingUiProvider.getKey();
	}
}
