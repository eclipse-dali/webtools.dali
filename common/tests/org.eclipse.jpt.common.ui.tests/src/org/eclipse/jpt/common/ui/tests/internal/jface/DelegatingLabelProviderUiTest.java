/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.jface;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.jface.AbstractItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.internal.jface.ItemTreeStateProviderManager;
import org.eclipse.jpt.common.ui.internal.jface.StaticItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider.Factory;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProviderFactory;
import org.eclipse.jpt.common.ui.jface.TreeStateProvider;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

@SuppressWarnings("nls")
public class DelegatingLabelProviderUiTest
	extends ApplicationWindow
{
	private TreeViewer tree;

	/* CU private */ ModifiablePropertyValueModel<Vehicle> selectedVehicleModel = new SimplePropertyValueModel<Vehicle>();


	public static void main(String[] args) {
		Window window = new DelegatingLabelProviderUiTest();
		window.setBlockOnOpen(true);
		window.open();

		Display.getCurrent().dispose();
		System.exit(0);
	}


	private DelegatingLabelProviderUiTest() {
		super(null);
	}


	@Override
	protected Control createContents(Composite parent) {
		((Shell) parent).setText(this.getClass().getSimpleName());
		parent.setSize(400, 400);
		parent.setLayout(new GridLayout());
		Composite mainPanel = new Composite(parent, SWT.NONE);
		mainPanel.setLayoutData(new GridData(GridData.FILL_BOTH));
		mainPanel.setLayout(new GridLayout());
		this.buildTreePanel(mainPanel);
		this.buildControlPanel(mainPanel);
		return mainPanel;
	}

	private void buildTreePanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL_BOTH));
		panel.setLayout(new GridLayout());

		Label label = new Label(panel, SWT.NONE);
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		label.setText("My Vehicles");

		this.tree = new TreeViewer(panel, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		this.tree.getTree().setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true));
		TreeStateProvider contentAndLabelProvider =
			new ItemTreeStateProviderManager(
				new VehicleContentProviderFactory(),
				new VehicleLabelProviderFactory(),
				JFaceResources.getResources());
		this.tree.setContentProvider(contentAndLabelProvider);
		this.tree.setLabelProvider(contentAndLabelProvider);
		this.tree.setInput(new Root());
		this.tree.addSelectionChangedListener(this.buildTreeSelectionChangedListener());
	}

	private ISelectionChangedListener buildTreeSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				DelegatingLabelProviderUiTest.this.selectedVehicleModel.setValue((Vehicle) ((IStructuredSelection) event.getSelection()).getFirstElement());
			}
		};
	}

	private void buildControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		panel.setLayout(new GridLayout());
		this.buildUpperControlPanel(panel);
		this.buildLowerControlPanel(panel);
	}

	private void buildUpperControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		panel.setLayout(new GridLayout(2, true));
		this.buildVehicleCombo(panel);
		this.buildColorCombo(panel);
	}

	private void buildVehicleCombo(Composite parent) {
		final ComboViewer combo = new ComboViewer(parent, SWT.READ_ONLY);
		combo.getCombo().setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new VehicleTypeLabelProvider());
		combo.setInput(
			new VehicleType[] {
				VehicleType.BICYCLE, VehicleType.CAR,
				VehicleType.TRUCK, VehicleType.BOAT
			});
		combo.getCombo().setEnabled(false);
		combo.addSelectionChangedListener(
			new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					DelegatingLabelProviderUiTest.this.getSelectedVehicle().setVehicleType((VehicleType) ((StructuredSelection) event.getSelection()).getFirstElement());
				}
			});
		this.selectedVehicleModel.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = DelegatingLabelProviderUiTest.this.getSelectedVehicle();
					combo.getCombo().setEnabled(vehicle != null);
					combo.setSelection(new StructuredSelection((vehicle == null) ? null : vehicle.getVehicleType()));
				}
			});
	}

	private void buildColorCombo(Composite parent) {
		final ComboViewer combo = new ComboViewer(parent, SWT.READ_ONLY);
		combo.getCombo().setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new ColorLabelProvider());
		combo.setInput(new VehicleColor[] {VehicleColor.RED, VehicleColor.BLUE, VehicleColor.YELLOW, VehicleColor.GREEN});
		combo.addSelectionChangedListener(
			new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					DelegatingLabelProviderUiTest.this.getSelectedVehicle().setColor((VehicleColor) ((StructuredSelection) event.getSelection()).getFirstElement());
				}
			});
		this.selectedVehicleModel.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = DelegatingLabelProviderUiTest.this.getSelectedVehicle();
					combo.getCombo().setEnabled(vehicle != null);
					combo.setSelection(new StructuredSelection((vehicle == null) ? null : vehicle.getColor()));
				}
			});
	}

	private void buildLowerControlPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.FILL, GridData.CENTER, true, false));
		panel.setLayout(new GridLayout(3, false));
		this.buildEffectsLabel(panel);
		this.buildGrayedCheckBox(panel);
		this.buildTranslucentCheckBox(panel);
		this.buildActionPanel(panel);
	}

	private void buildEffectsLabel(Composite parent) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText("Color effects: ");
		label.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false, 3, 1));
	}

	private void buildGrayedCheckBox(Composite parent) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, false, false));
		button.setText("grayed");
		button.setEnabled(false);
		button.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					DelegatingLabelProviderUiTest.this.getSelectedVehicle().setGrayed(button.getSelection());
				}
			});
		this.selectedVehicleModel.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = DelegatingLabelProviderUiTest.this.getSelectedVehicle();
					button.setEnabled(vehicle != null);
					button.setSelection((vehicle != null) && vehicle.isGrayed());
				}
			});
	}

	private void buildTranslucentCheckBox(Composite parent) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setLayoutData(new GridData(GridData.BEGINNING, GridData.CENTER, true, false));
		button.setText("translucent");
		button.setEnabled(false);
		button.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					DelegatingLabelProviderUiTest.this.getSelectedVehicle().setTranslucent(button.getSelection());
				}
			});
		this.selectedVehicleModel.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					Vehicle vehicle = DelegatingLabelProviderUiTest.this.getSelectedVehicle();
					button.setEnabled(vehicle != null);
					button.setSelection((vehicle != null) && vehicle.isTranslucent());
				}
			});
	}

	private void buildActionPanel(Composite parent) {
		Composite panel = new Composite(parent, SWT.NONE);
		panel.setLayoutData(new GridData(GridData.END, GridData.FILL, false, false));
		panel.setLayout(new GridLayout());
		this.buildRefreshTreeACI().fill(panel);
	}

	private ActionContributionItem buildRefreshTreeACI() {
		Action action = new Action("Refresh tree", IAction.AS_PUSH_BUTTON) {
			@Override
			public void run() {
				DelegatingLabelProviderUiTest.this.refreshTree();
			}
		};
		action.setToolTipText("Refresh the tree's labels");
		return new ActionContributionItem(action);
	}

	void refreshTree() {
		this.tree.refresh();
	}

	Vehicle getSelectedVehicle() {
		return this.selectedVehicleModel.getValue();
	}


	/* CU private */ static class VehicleTypeLabelProvider
		extends BaseLabelProvider
		implements ILabelProvider
	{
		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			return ((VehicleType) element).getDescription();
		}
	}


	/* CU private */ static class ColorLabelProvider
		extends BaseLabelProvider
		implements ILabelProvider
	{
		public Image getImage(Object element) {
			return null;
		}

		public String getText(Object element) {
			return ((VehicleColor) element).getDescription();
		}
	}


	/* CU private */ static class VehicleContentProviderFactory
		implements ItemTreeContentProviderFactory
	{
		public ItemTreeContentProvider buildProvider(Object item, ItemTreeContentProvider.Manager manager) {
			if (item instanceof Root) {
				return this.buildRootProvider((Root) item);
			}
			return this.buildVehicleProvider((Vehicle) item);
		}
		protected ItemTreeContentProvider buildRootProvider(Root item) {
			return new StaticItemTreeContentProvider(null, item.getVehicles());
		}
		protected ItemTreeContentProvider buildVehicleProvider(Vehicle item) {
			return new StaticItemTreeContentProvider(item.parent());
		}
	}


	/* CU private */ static class VehicleLabelProviderFactory
		implements ItemExtendedLabelProvider.Factory
	{
		public ItemExtendedLabelProvider buildProvider(Object item, ItemExtendedLabelProvider.Manager manager) {
			return new VehicleLabelProvider((Vehicle) item, manager);
		}
	}


	/* CU private */ static class VehicleLabelProvider
		extends AbstractItemExtendedLabelProvider<Vehicle>
	{
		public VehicleLabelProvider(Vehicle vehicle, ItemExtendedLabelProvider.Manager manager) {
			super(vehicle, manager);
		}

		@Override
		protected PropertyValueModel<ImageDescriptor> buildImageDescriptorModel() {
			return new PropertyAspectAdapter<Vehicle, ImageDescriptor>(IMAGE_ASPECT_NAMES, this.item) {
				@Override
				protected ImageDescriptor buildValue_() {
					return this.subject.getImageDescriptor();
				}
			};
		}
		private static final String[] IMAGE_ASPECT_NAMES =
				new String[] {
					Vehicle.COLOR_PROPERTY,
					Vehicle.GRAYED_PROPERTY,
					Vehicle.TRANSLUCENT_PROPERTY
				};

		@Override
		protected PropertyValueModel<String> buildTextModel() {
			return new PropertyAspectAdapter<Vehicle, String>(TEXT_ASPECT_NAMES, this.item) {
				@Override
				protected String buildValue_() {
					return this.subject.getColor().getDescription() + ' ' + this.subject.getVehicleType().getDescription();
				}
			};
		}
		private static final String[] TEXT_ASPECT_NAMES =
				new String[] {
					Vehicle.VEHICLE_TYPE_PROPERTY,
					Vehicle.COLOR_PROPERTY
				};

		@Override
		protected PropertyValueModel<String> buildDescriptionModel() {
			return this.buildTextModel();
		}
	}


	private static abstract class TreeNode
		extends AbstractModel
	{
		private TreeNode parent;

		public TreeNode(TreeNode parent) {
			this.parent = parent;
		}

		public TreeNode parent() {
			return this.parent;
		}
	}


	private static class Root
		extends TreeNode
	{
		protected final Vehicle[] vehicles;

		public Root() {
			super(null);
			this.vehicles = this.buildVehicles();
		}

		private Vehicle[] buildVehicles() {
			return new Vehicle[] {
					new Vehicle(this, VehicleType.BICYCLE, VehicleColor.BLUE),
					new Vehicle(this, VehicleType.CAR, VehicleColor.YELLOW),
					new Vehicle(this, VehicleType.TRUCK, VehicleColor.RED),
					new Vehicle(this, VehicleType.BOAT, VehicleColor.GREEN)
				};
		}

		public Vehicle[] getVehicles() {
			return this.vehicles;
		}
	}


	/* CU private */ static class Vehicle
		extends TreeNode
	{
		private VehicleType vehicleType;
		public final static String VEHICLE_TYPE_PROPERTY = "vehicleType";

		private VehicleColor color;
		public final static String COLOR_PROPERTY = "color";

		private boolean grayed = false;
		public final static String GRAYED_PROPERTY = "grayed";

		private boolean translucent = false;
		public final static String TRANSLUCENT_PROPERTY = "translucent";


		Vehicle(TreeNode parent, VehicleType vehicleType, VehicleColor color) {
			super(parent);
			this.vehicleType = vehicleType;
			this.color = color;
		}

		public VehicleType getVehicleType() {
			return this.vehicleType;
		}

		public void setVehicleType(VehicleType vehicleType) {
			VehicleType old = this.vehicleType;
			this.vehicleType = vehicleType;
			this.firePropertyChanged(VEHICLE_TYPE_PROPERTY, old, vehicleType);
		}

		public VehicleColor getColor() {
			return this.color;
		}

		public void setColor(VehicleColor color) {
			VehicleColor old = this.color;
			this.color = color;
			this.firePropertyChanged(COLOR_PROPERTY, old, color);
		}

		public boolean isGrayed() {
			return this.grayed;
		}

		public void setGrayed(boolean grayed) {
			boolean old = this.grayed;
			this.grayed = grayed;
			this.firePropertyChanged(GRAYED_PROPERTY, old, grayed);
		}

		public boolean isTranslucent() {
			return this.translucent;
		}

		public void setTranslucent(boolean translucent) {
			boolean old = this.translucent;
			this.translucent = translucent;
			this.firePropertyChanged(TRANSLUCENT_PROPERTY, old, translucent);
		}

		public ImageDescriptor getImageDescriptor() {
			return VehicleImageDescriptorFactory.buildImageDescriptor(this.color, this.grayed, this.translucent);
		}
	}


	/* CU private */ static enum VehicleType {
		BICYCLE("bicycle"),
		CAR("car"),
		TRUCK("truck"),
		BOAT("boat");

		private final String description;

		private VehicleType(String description) {
			this.description = description;
		}

		public String getDescription() {
			return this.description;
		}

		@Override
		public String toString() {
			return this.description;
		}
	}


	/* CU private */ static enum VehicleColor {
		RED("red", new RGB(255, 0, 0)),
		BLUE("blue", new RGB(0, 0, 255)),
		YELLOW("yellow", new RGB(255, 255, 0)),
		GREEN("green", new RGB(0, 255, 0));

		private final String description;

		private final RGB rgb;

		private VehicleColor(String description, RGB rgb) {
			this.description = description;
			this.rgb = rgb;
		}

		public String getDescription() {
			return this.description;
		}

		public RGB rgb() {
			return this.rgb;
		}

		@Override
		public String toString() {
			return this.description;
		}
	}


	/* CU private */ static class VehicleImageDescriptorFactory {
		static ImageDescriptor buildImageDescriptor(VehicleColor color, boolean grayed, boolean translucent) {
			PaletteData pd = new PaletteData(new RGB[] { buildRGB(color, grayed, translucent) });
			ImageData imageData = new ImageData(20, 20, 1, pd);
			for (int x = 0; x < 20; x ++) {
				for (int y = 0; y < 20; y ++) {
					imageData.setPixel(x, y, 0);
				}
			}
			return ImageDescriptor.createFromImageData(imageData);
		}

		private static RGB buildRGB(VehicleColor color, boolean grayed, boolean translucent) {
			RGB rgb = (grayed) ? GRAY : color.rgb();
			if (translucent) {
				rgb = new RGB(translucify(rgb.red), translucify(rgb.green), translucify(rgb.blue));
			}
			return rgb;
		}
		private static final RGB GRAY = new RGB(127, 127, 127);

		private static int translucify(int colorComponent) {
			return 255 - (int) ((255 - colorComponent) * 0.3);
		}
	}
}
