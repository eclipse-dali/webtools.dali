/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.swt.widgets.Composite;

/**
 * A mapping UI definition provides the {@link #getLabel() label} and
 * {@link #getImageDescriptor() image descriptor} for the
 * (type or attribute) mapping type indicated by {@link #getKey()}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.0
 */
public interface MappingUiDefinition {

	/**
	 * Return a key corresponding to the mapping's key.
	 * 
	 * @see org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition#getKey()
	 * @see org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition#getKey()
	 * @see org.eclipse.jpt.jpa.core.context.orm.OrmTypeMappingDefinition#getKey()
	 * @see org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition#getKey()
	 */
	String getKey();
	Transformer<MappingUiDefinition, String> KEY_TRANSFORMER = new KeyTransformer();
	class KeyTransformer
		extends AbstractTransformer<MappingUiDefinition, String>
	{
		@Override
		public String transform_(MappingUiDefinition def) {
			return def.getKey();
		}
	}

	/**
	 * Return a string corresponding to the mapping type.
	 */
	String getLabel();
	Transformer<MappingUiDefinition, String> LABEL_TRANSFORMER = new LabelTransformer();
	class LabelTransformer
		extends AbstractTransformer<MappingUiDefinition, String>
	{
		@Override
		public String transform_(MappingUiDefinition def) {
			return def.getLabel();
		}
	}

	/**
	 * Return a string that corresponds to the mapping type and can be used
	 * in the mapping change link label.
	 */
	String getLinkLabel();

	/**
	 * Create a JPA composite corresponding to the definition's mapping type.
	 * This will be displayed by the JPA details view
	 * when the mapping key matches the definition's key.
	 */
	JpaComposite buildMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<? extends JpaContextModel> nodeModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager);

	/**
	 * Return an image descriptor corresponding to the mapping type.
	 */
	ImageDescriptor getImageDescriptor();

	Transformer<MappingUiDefinition, ImageDescriptor> IMAGE_DESCRIPTOR_TRANSFORMER = new ImageDescriptorTransformer();
	class ImageDescriptorTransformer
		extends AbstractTransformer<MappingUiDefinition, ImageDescriptor>
	{
		@Override
		public ImageDescriptor transform_(MappingUiDefinition def) {
			return def.getImageDescriptor();
		}
	}

	/**
	 * Return whether the ui represented by this definition is enabled for the given
	 * node.  This is almost always true.
	 */
	boolean isEnabledFor(JpaContextModel node);

	/* CU private */ class IsEnabledFor
		extends CriterionPredicate<MappingUiDefinition, JpaContextModel>
	{
		public IsEnabledFor(JpaContextModel node) {
			super(node);
		}
		public boolean evaluate(MappingUiDefinition mappingUiDefinition) {
			return mappingUiDefinition.isEnabledFor(this.criterion);
		}
	}


	// ********** null composite **********

	static class NullComposite
		extends Pane<JpaContextModel>
		implements JpaComposite
	{
		public NullComposite(PropertyValueModel<? extends JpaContextModel> mappingModel, Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
			super(mappingModel, parent, widgetFactory, resourceManager);
		}

		@Override
		protected void initializeLayout(Composite container) {
			// NOP
		}
	}
}
