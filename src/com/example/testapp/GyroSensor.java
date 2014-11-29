package appcompat_v7;

import android.annotation.TargetApi;
import android.hardware.*;
import android.os.Build;

public class GyroSensor {
	


	private static final float NS2S = 1.0f / 1000000000.0f;
	private final float[] deltaRotationVector = new float[4];
	private float timestamp;

	@TargetApi(Build.VERSION_CODES.GINGERBREAD) public float[] onSensorChanged(SensorEvent event) {
	  // This timestep's delta rotation to be multiplied by the current rotation
	  // after computing it from the gyro sample data.
	  if (timestamp != 0) {
	    final float dT = (event.timestamp - timestamp) * NS2S;
	    // Axis of the rotation sample, not normalized yet.
	    float axisX = event.values[0];
	    float axisY = event.values[1];
	    float axisZ = event.values[2];

	    // Calculate the angular speed of the sample
	    float omegaMagnitude = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

	    // Normalize the rotation vector if it's big enough to get the axis
	    // (that is, EPSILON should represent your maximum allowable margin of error)
	    float EPSILON = (float) (Math.PI/20);
	    if (omegaMagnitude > EPSILON) {
	      axisX /= omegaMagnitude;
	      axisY /= omegaMagnitude;
	      axisZ /= omegaMagnitude;
	    }

	    // Integrate around this axis with the angular speed by the timestep
	    // in order to get a delta rotation from this sample over the timestep
	    // We will convert this axis-angle representation of the delta rotation
	    // into a quaternion before turning it into the rotation matrix.
	    float thetaOverTwo = omegaMagnitude * dT / 2.0f;
	    float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
	    float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
	    deltaRotationVector[0] = sinThetaOverTwo * axisX;
	    deltaRotationVector[1] = sinThetaOverTwo * axisY;
	    deltaRotationVector[2] = sinThetaOverTwo * axisZ;
	    deltaRotationVector[3] = cosThetaOverTwo;
	  }
	  timestamp = event.timestamp;
	  float[] deltaRotationMatrix = new float[9];
	  SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
	    // User code should concatenate the delta rotation we computed with the current rotation
	    // in order to get the updated rotation.
	    // rotationCurrent = rotationCurrent * deltaRotationMatrix;
        return deltaRotationMatrix;
	   }
	}

	

