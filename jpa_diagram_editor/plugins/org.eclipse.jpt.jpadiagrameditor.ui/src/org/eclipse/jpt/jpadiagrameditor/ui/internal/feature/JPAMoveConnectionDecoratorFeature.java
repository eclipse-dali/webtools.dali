package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IMoveConnectionDecoratorContext;
import org.eclipse.graphiti.features.impl.DefaultMoveConnectionDecoratorFeature;

public class JPAMoveConnectionDecoratorFeature extends
		DefaultMoveConnectionDecoratorFeature {

	public JPAMoveConnectionDecoratorFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public boolean canExecute(IContext context) {
		return false;
	}
	
	@Override
	public boolean canMoveConnectionDecorator(
			IMoveConnectionDecoratorContext context) {
		return false;
	}
	
	@Override
	public boolean isAvailable(IContext context) {
		return false;
	}	

}
