package view.menus;

import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingBox;
import com.jme3.input.ChaseCamera;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import model.map.Cell;

public class MapRenderer extends SimpleApplication {
    private Cell[][] cellMap;
    private ChaseCamera chaseCam;
    private Camera chaseCamCamera; // Added private Camera variable
    private float rotationSpeed = 0.0025f;
    private float minVerticalRotation = -FastMath.HALF_PI; // -90 degrees
    private float maxVerticalRotation = FastMath.HALF_PI;  // 90 degrees
    private float minDistance = 10;
    private float maxDistance = 50;

    public MapRenderer(CellMap cellMap) {
        this.cellMap = cellMap;
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false); // Disable the default fly camera

        // Create a ChaseCamera to control the camera movement
        chaseCamCamera = cam; // Set the private Camera variable
        chaseCam = new ChaseCamera(chaseCamCamera, inputManager); // Pass the chaseCamCamera to the constructor
        chaseCam.setDragToRotate(false);
        chaseCam.setSmoothMotion(true);
        chaseCam.setInvertVerticalAxis(true);
        chaseCam.setDefaultDistance(20);
        chaseCam.setMinVerticalRotation(minVerticalRotation);
        chaseCam.setMaxVerticalRotation(maxVerticalRotation);
        chaseCam.setMinDistance(minDistance);
        chaseCam.setMaxDistance(maxDistance);

        // Set up mouse drag to move the camera
        inputManager.addMapping("MouseDrag", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addListener(mouseActionListener, "MouseDrag");
        inputManager.addMapping("MouseMovement", new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        inputManager.addListener(mouseAnalogListener, "MouseMovement");

        // Create a spatial for the map
        Spatial mapSpatial = new Node("Map");
        mapSpatial.setModelBound(new BoundingBox()); // Set an initial bounding box

        // Set the map limits for camera movement
        float mapWidth = cellMap.getWidth() * cellMap.getCellSize();
        float mapHeight = cellMap.getHeight() * cellMap.getCellSize();
        chaseCam.setMinVerticalRotation(minVerticalRotation);
        chaseCam.setMaxVerticalRotation(maxVerticalRotation);

        // Set the map bounding box to limit camera position
        BoundingBox mapBounds = new BoundingBox(
                new Vector3f(-mapWidth / 2, -10, -mapHeight / 2),
                new Vector3f(mapWidth / 2, 10, mapHeight / 2)
        );
        mapSpatial.setModelBound(mapBounds);
        mapSpatial.updateModelBound();

        // Attach the map spatial to the scene
        rootNode.attachChild(mapSpatial);

        // Render the cell map
        renderCellMap(cellMap.getCells(), (Node) mapSpatial);
    }

    private void renderCellMap(Cell[][] cellMap, Node mapSpatial) {
        // Load the stone-like texture
        Texture texture = assetManager.loadTexture("Textures/rock_texture.jpg");
        texture.setWrap(Texture.WrapMode.Repeat);

        for (int x = 0; x < cellMap.length; x++) {
            for (int y = 0; y < cellMap[x].length; y++) {
                Cell cell = cellMap[x][y];

                // Calculate the actual position and size of the cell
                float xPos = cell.getX() * cell.getSize();
                float yPos = cell.getY() * cell.getSize();
                float cellSize = cell.getSize();

                // Create a JMonkeyEngine geometry to represent the cell
                Box box = new Box(cellSize, cellSize, cellSize);
                Geometry geometry = new Geometry("view.Cell", box);

                // Move the cell to its appropriate position
                geometry.setLocalTranslation(xPos, 0, yPos);

                // Create a material and set the stone-like texture
                Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                material.setTexture("ColorMap", texture);
                geometry.setMaterial(material);

                // Attach the cell geometry to the map spatial
                mapSpatial.attachChild(geometry);
            }
        }
    }

    private ActionListener mouseActionListener = (String name, boolean isPressed, float tpf) -> {
        // TODO: Handle mouse button action (if needed)
    };

    private AnalogListener mouseAnalogListener = (String name, float value, float tpf) -> {
        // Handle mouse movement to rotate the camera
        if (name.equals("MouseMovement")) {
            float rotation = value * rotationSpeed;
            Quaternion rotationQuat = new Quaternion().fromAngleAxis(rotation, Vector3f.UNIT_Y);

            // Rotate the camera direction
            Vector3f direction = chaseCamCamera.getDirection();
            Vector3f up = chaseCamCamera.getUp();
            Quaternion directionQuat = new Quaternion();
            directionQuat.lookAt(direction, up);

            Quaternion resultQuat = rotationQuat.mult(directionQuat).mult(rotationQuat.inverse());
            Vector3f newDirection = resultQuat.mult(direction);

            // Update the camera direction
            chaseCamCamera.lookAtDirection(newDirection, up);
        }
    };

    public static void main(String[] args) {
        CellMap cellMap = new CellMap(10, 10, 1); // Example parameters

        MapRenderer app = new MapRenderer(cellMap);
        app.start();
    }
}
