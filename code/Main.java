import java.awt.*;
import java.awt.event.KeyEvent;


public class Main {
    public static void main(String[] args){

        // GIVEN VARIABLES

        double xScale = 800.0, yScale = 400.0;
        StdDraw.setCanvasSize(800, 400);
        StdDraw.setXscale(0.0, xScale);
        StdDraw.setYscale(0.0, yScale);

        // Color array for bricks (first import java.awt.Color )
        Color[] colors = { new Color(255, 0, 0), new Color(220, 20, 60),
                new Color(178, 34, 34), new Color(139, 0, 0),
                new Color(255, 69, 0), new Color(165, 42, 42)
        };

        // Game Components (These can be changed for custom scenarios)
        double ballRadius = 8; // Ball radius
        double ballVelocity = 5; // Magnitude of the ball velocity
        Color ballColor = new Color(15, 82, 186); // Color of the ball
        double[] initialBallPos = {400,18}; //Initial position of the ball in the format {x, y}
        double[] paddlePos = {400, 5}; // Initial position of the center of the paddle
        double paddleHalfwidth = 60; // Paddle halfwidth
        double paddleHalfheight = 5; // Paddle halfheight
        double paddleSpeed = 20; // Paddle speed
        Color paddleColor = new Color(128, 128, 128); // Paddle color
        double brickHalfwidth = 50; // Brick halfwidth
        double brickHalfheight = 10; // Brick halfheight

        // 2D array to store center coordinates of bricks in the format {x, y}
        double[][] brickCoordinates = new double[][]{
                {250, 320},{350, 320},{450, 320},{550, 320},
                {150,300},{250, 300},{350, 300},{450, 300},{550, 300},{650, 300},
                {50, 280},{150, 280},{250, 280},{350, 280},{450, 280},{550, 280},{650, 280},{750, 280},
                {50, 260},{150, 260},{250, 260},{350, 260},{450, 260},{550, 260},{650, 260},{750, 260},
                {50, 240},{150, 240},{250, 240},{350, 240},{450, 240},{550, 240},{650, 240},{750, 240},
                {150, 220},{250, 220},{350, 220},{450, 220},{550, 220},{650, 220},
                {250, 200},{350, 200},{450, 200},{550, 200}};

        // Brick colors
        Color[] brickColors = new Color[] {
                colors[0], colors[1], colors[2], colors[3],
                colors[2], colors[4], colors[3], colors[0], colors[4], colors[5],
                colors[5], colors[0], colors[1], colors[5], colors[2], colors[3], colors[0], colors[4],
                colors[1], colors[3], colors[2], colors[4], colors[0], colors[5], colors[2], colors[1],
                colors[4], colors[0], colors[5], colors[1], colors[2], colors[3], colors[0], colors[5],
                colors[1], colors[4], colors[0], colors[5], colors[1], colors[2],
                colors[3], colors[2], colors[3], colors[0]};

        // DOUBLES
        // Defining shooting direction and angle
        double shootingAngle = 0;
        double maxShootingAngle = 180;
        double minShootingAngle = 0;
        double shootingTrajectoryLength = 50;
        double trajectoryLengthX = (Math.cos(shootingAngle/180*Math.PI))*shootingTrajectoryLength;
        double trajectoryLengthY = (Math.sin(shootingAngle/180*Math.PI))*shootingTrajectoryLength;
        double[] shootingLineStart = initialBallPos;
        double[] shootingLineEnd = {initialBallPos[0]+trajectoryLengthX,initialBallPos[1]+trajectoryLengthY};

        // Creating a new variable to keep recent ball position
        double[] newBallPos = {initialBallPos[0],initialBallPos[1]};

        // Defining shooting velocities
        double velocityOnX;
        double velocityOnY;

        // Defining angles for collision mechanism.
        double theta;
        double phi;

        // Defining velocities after a corner collision
        double newVelocityOnX;
        double newVelocityOnY;

        //INTEGERS
        // Defining player's score
        int scoreOfPlayer = 0;
        int scoreForOneBrick = 10;

        // Determining the frame time
        int frameTime = 15;

        // Defining the number of bricks
        int numOfBricks = brickColors.length;

        //BOOLEANS
        // Counting how many times space was pressed for pause statement
        boolean isSpacePressed = false ;

        // Controlling whether the game is paused
        boolean isPaused = false;

        // Controlling whether the game initialized, over or won
        boolean isGameInitialized = false;
        boolean isGameOver = false;
        boolean isVictory = false;


        // Controlling whether there exists a brick near the brick
        boolean isLeft;
        boolean isRight;
        boolean isTop;
        boolean isBottom;

        // Creating an array to check whether the brick is hit.
        boolean[] brickHitStatus = new boolean[numOfBricks];

        StdDraw.enableDoubleBuffering();


        // Creating the playing screen for angle selection.
        // When the game initializes, this loop terminates.
        while (!isGameInitialized){

            StdDraw.pause(frameTime);
            StdDraw.clear();

            // Increasing angle until 180 degrees.
            if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)){
                shootingAngle++;
                if (shootingAngle > maxShootingAngle){
                    shootingAngle = maxShootingAngle;
                }
            }

            // Decreasing angle until 180 degrees.
            if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)){
                shootingAngle--;
                if (shootingAngle < minShootingAngle){
                    shootingAngle = minShootingAngle;
                }
            }

            // Initializing the game
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)){
                isGameInitialized = true;
                // Record space is pressed once
                isSpacePressed = true;
            }

            // Adjusting trajectory line before shooting
            trajectoryLengthX = (Math.cos(shootingAngle/180*Math.PI))*shootingTrajectoryLength;
            trajectoryLengthY = (Math.sin(shootingAngle/180*Math.PI))*shootingTrajectoryLength;
            shootingLineEnd = new double[]{initialBallPos[0] + trajectoryLengthX, initialBallPos[1] + trajectoryLengthY};

            //Placing line to the canvas
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(shootingLineStart[0],shootingLineStart[1],shootingLineEnd[0],shootingLineEnd[1]);

            // Placing ball to the canvas
            StdDraw.setPenColor(ballColor);
            StdDraw.filledCircle(initialBallPos[0],initialBallPos[1],ballRadius);

            // Placing paddle to the canvas
            StdDraw.setPenColor(paddleColor);
            StdDraw.filledRectangle(paddlePos[0],paddlePos[1],paddleHalfwidth,paddleHalfheight);

            // Placing bricks to the canvas
            for(int i = 0 ; i < numOfBricks ; i++){
                StdDraw.setPenColor(brickColors[i]);
                StdDraw.filledRectangle(brickCoordinates[i][0],brickCoordinates[i][1],brickHalfwidth,brickHalfheight);
            }

            // Writing angle and score
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.textLeft(20,380, "Angle: " + shootingAngle);
            StdDraw.textLeft(680,380, "Score: " + scoreOfPlayer);

            StdDraw.show();
        }


        //Redefining velocities
        velocityOnX = ballVelocity * (Math.cos(shootingAngle/180*Math.PI));
        velocityOnY = ballVelocity * (Math.sin(shootingAngle/180*Math.PI));

        // Creating the playing animation
        // When the game is over or is won, this loop terminates.
        while (!isGameOver && !isVictory){

            // Creating a pause statement
            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && !isSpacePressed) {
                isSpacePressed = true;
                isPaused = !isPaused;
            }
            // Turning immediately space condition to "not pressed" to prepare for the next press.
            if (!StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                isSpacePressed = false;
            }

            // Paused game
            if (isPaused){

                StdDraw.pause(frameTime);
                StdDraw.clear();

                // Placing ball to the canvas
                StdDraw.setPenColor(ballColor);
                StdDraw.filledCircle(newBallPos[0],newBallPos[1],ballRadius);

                // Placing paddle to the canvas
                StdDraw.setPenColor(paddleColor);
                StdDraw.filledRectangle(paddlePos[0],paddlePos[1],paddleHalfwidth,paddleHalfheight);

                // Placing bricks to the canvas
                for(int i = 0 ; i < numOfBricks ; i++){
                    if(!brickHitStatus[i]){
                        StdDraw.setPenColor(brickColors[i]);
                        StdDraw.filledRectangle(brickCoordinates[i][0], brickCoordinates[i][1],brickHalfwidth,brickHalfheight);
                    }
                }
                // Placing pause statement
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.textLeft(20,380, "Paused");

                // Writing score
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.textLeft(680,380, "Score: " + scoreOfPlayer);

                StdDraw.show();

            }
            else {
                StdDraw.pause(frameTime);
                StdDraw.clear();

                // Shooting the ball
                newBallPos = new double[] {newBallPos[0] + velocityOnX , newBallPos[1] + velocityOnY};

                // Setting theta
                theta = thetaFinder(velocityOnX,velocityOnY);


                // Moving the paddle until the left end.
                if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)){
                    paddlePos = new double[] {paddlePos[0] - paddleSpeed , paddlePos[1]};
                    if (paddlePos[0] < paddleHalfwidth ) {
                        paddlePos[0] = paddleHalfwidth;
                    }
                }

                // Moving the paddle until the right end.
                if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)){
                    paddlePos = new double[] {paddlePos[0] + paddleSpeed , paddlePos[1]};
                    if (paddlePos[0] > xScale - paddleHalfwidth) {
                        paddlePos[0] = xScale - paddleHalfwidth;
                    }
                }

                // Preventing the paddle bug.
                if (Math.abs(newBallPos[0] - paddlePos[0]) <= paddleHalfwidth
                        && newBallPos[1] < paddleHalfheight){
                    newBallPos[1] = 2 * paddleHalfheight + ballRadius;
                }

                // Ball collided with left wall.
                if (newBallPos[0] <= ballRadius) {
                    velocityOnX = - velocityOnX;
                    newBallPos[0] += distanceFinder(0,newBallPos[0],"left",ballRadius);
                }
                // Ball collided with right wall.
                if (newBallPos[0] >= xScale - ballRadius){
                    velocityOnX = - velocityOnX;
                    newBallPos[0] += distanceFinder(xScale,newBallPos[0],"right",ballRadius);
                }

                // Ball collided with ceiling.
                if (newBallPos[1] >= yScale - ballRadius){
                    velocityOnY = - velocityOnY;
                    newBallPos[1] += distanceFinder(yScale,newBallPos[1],"top",ballRadius);
                }

                double leftDistancePaddleOnX = newBallPos[0]  - (paddlePos[0] - paddleHalfwidth);
                double rightDistancePaddleOnX = newBallPos[0] - (paddlePos[0] + paddleHalfwidth);
                double distancePaddleOnY = newBallPos[1]  - (paddlePos[1] + paddleHalfheight);

                // Ball collided with top of the paddle.
                if (((Math.abs(newBallPos[0] - paddlePos[0]) <= paddleHalfwidth)
                        && newBallPos[1]  < ballRadius + 2 * paddleHalfheight)) {
                    velocityOnY = - velocityOnY ;
                    newBallPos[1] += distanceFinder(2 * paddleHalfheight,newBallPos[1],"bottom",ballRadius);
                }

                // Ball collided with right and left of the paddle.
                else if ((Math.abs(newBallPos[1] - paddlePos[1]) <= paddleHalfheight)
                        && (newBallPos[0] <= paddlePos[0] + paddleHalfwidth + ballRadius
                        && newBallPos[0] >= paddlePos[0] - paddleHalfwidth - ballRadius )) {
                    if (newBallPos[0] > paddlePos[0])
                        newBallPos[0] += distanceFinder((paddlePos[0] + paddleHalfwidth),
                                newBallPos[0],"left",ballRadius);
                    else {
                        newBallPos[0] += distanceFinder((paddlePos[0] - paddleHalfwidth),
                                newBallPos[0],"right",ballRadius);
                    }
                    velocityOnX = -velocityOnX;
                }
                // Top left corner
                else if ((Math.sqrt(Math.pow(leftDistancePaddleOnX,2) + Math.pow(distancePaddleOnY,2)) <= ballRadius)
                        && (newBallPos[0]  < paddlePos[0] - paddleHalfwidth)
                        && (newBallPos[1]  > 2 * paddleHalfheight)
                        && (newBallPos[0]  > paddlePos[0] - paddleHalfwidth - ballRadius)
                        && (newBallPos[1]  < 2 * paddleHalfheight + ballRadius)){
                    // Determining paddle's corners.
                    double paddleCornerX = -paddleHalfwidth + paddlePos[0];
                    double paddleCornerY = paddleHalfheight + paddlePos[1];
                    // Finding the portion which will be reflected.
                    double portion = portionFinder(newBallPos,velocityOnX,velocityOnY,
                            ballRadius,paddleCornerX,paddleCornerY);
                    // Preventing ball to go in the paddle during collision by subtracting the distance having gone inside.
                    newBallPos[0] -= (portion * velocityOnX);
                    newBallPos[1] -= (portion * velocityOnY);
                    // Calculating new velocities after a collision.
                    phi = phiFinder(newBallPos,paddlePos,paddleHalfheight,paddleHalfwidth);
                    newVelocityOnX = ballVelocity * Math.cos(Math.PI + 2 * phi - theta);
                    newVelocityOnY = ballVelocity * Math.sin(Math.PI + 2 * phi - theta);
                    velocityOnX = newVelocityOnX;
                    velocityOnY = newVelocityOnY;
                    // Adding subtracted portion to collided ball.
                    newBallPos[0] += (portion * velocityOnX);
                    newBallPos[1] += (portion * velocityOnY);
                }

                // Top right corner
                else if ((Math.sqrt(Math.pow(rightDistancePaddleOnX,2) + Math.pow(distancePaddleOnY,2)) <= ballRadius)
                        && (newBallPos[0]  > paddlePos[0] + paddleHalfwidth)
                        && (newBallPos[1]  > 2 * paddleHalfheight)
                        && (newBallPos[0]  < paddlePos[0] + paddleHalfwidth + ballRadius)
                        && (newBallPos[1]  < 2 * paddleHalfheight + ballRadius)){
                    // Determining paddle's corners.
                    double paddleCornerX = paddleHalfwidth + paddlePos[0];
                    double paddleCornerY = paddleHalfheight + paddlePos[1];
                    // Finding the portion which will be reflected.
                    double portion = portionFinder(newBallPos,velocityOnX,velocityOnY,
                            ballRadius,paddleCornerX,paddleCornerY);
                    // Preventing ball to go in the paddle during collision by subtracting the distance having gone inside.
                    newBallPos[0] -= (portion * velocityOnX);
                    newBallPos[1] -= (portion * velocityOnY);
                    // Calculating new velocities after a collision.
                    phi = phiFinder(newBallPos,paddlePos,paddleHalfheight,paddleHalfwidth);
                    newVelocityOnX = ballVelocity * Math.cos(Math.PI + 2 * phi - theta);
                    newVelocityOnY = ballVelocity * Math.sin(Math.PI + 2 * phi - theta);
                    velocityOnX = newVelocityOnX;
                    velocityOnY = newVelocityOnY;
                    // Adding subtracted portion to collided ball.
                    newBallPos[0] += (portion * velocityOnX);
                    newBallPos[1] += (portion * velocityOnY);
                }

                for (int i = 0; i < numOfBricks; i++){


                    double leftDistanceOnX = newBallPos[0] - (brickCoordinates[i][0] - brickHalfwidth);
                    double bottomDistanceOnY = newBallPos[1] - (brickCoordinates[i][1] - brickHalfheight);
                    double rightDistanceOnX = newBallPos[0] - (brickCoordinates[i][0] + brickHalfwidth);
                    double topDistanceOnY = newBallPos[1] - (brickCoordinates[i][1] + brickHalfheight);

                    // Setting phi for bricks
                    phi = phiFinder(newBallPos,brickCoordinates[i],brickHalfheight,brickHalfwidth);

                    // Reassigning new velocity values for the bricks
                    newVelocityOnX = ballVelocity * Math.cos(Math.PI + 2 * phi - theta);
                    newVelocityOnY = ballVelocity * Math.sin(Math.PI + 2 * phi - theta);

                    // Controlling whether a brick exists near the i(th) block
                    double[] left = new double[2];
                    left[0] = brickCoordinates[i][0]- 2 * brickHalfwidth;
                    left[1] = brickCoordinates[i][1];
                    isLeft= contains(left,brickCoordinates,brickHitStatus);

                    double[] right = new double[2];
                    right[0] = brickCoordinates[i][0]+ 2 * brickHalfwidth;
                    right[1] = brickCoordinates[i][1];
                    isRight=contains(right,brickCoordinates,brickHitStatus);

                    double[] top= new double[2];
                    top[0] = brickCoordinates[i][0];
                    top[1] = brickCoordinates[i][1] + 2 * brickHalfheight;
                    isTop= contains(top,brickCoordinates,brickHitStatus);

                    double[] bottom = new double[2];
                    bottom[0] = brickCoordinates[i][0];
                    bottom[1] = brickCoordinates[i][1] - 2 * brickHalfheight;
                    isBottom= contains(bottom,brickCoordinates,brickHitStatus);

                    // If brick was hit, any assignments is made.
                    if (brickHitStatus[i]){
                        continue;
                    }
                    // Top left corner
                    else if ((Math.sqrt(Math.pow(leftDistanceOnX,2) + Math.pow(topDistanceOnY,2)) <= ballRadius)
                            && (newBallPos[0] <= brickCoordinates[i][0] - brickHalfwidth)
                            && (newBallPos[1] >= brickCoordinates[i][1] + brickHalfheight)
                            && (newBallPos[0] >= brickCoordinates[i][0] - brickHalfwidth - ballRadius)
                            && (newBallPos[1] <= brickCoordinates[i][1] + brickHalfheight + ballRadius)){
                        // If there is a block in left side during corner collision, the collision occurs as a surface collision.
                        if(isLeft){
                            velocityOnY = -velocityOnY;
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                            brickHitStatus[indexOf(left,brickCoordinates)] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                        }
                        // If there is a block in top side during corner collision, the collision occurs as a surface collision.
                        else if(isTop){
                            velocityOnX = -velocityOnX;
                            brickHitStatus[i] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                            brickHitStatus[indexOf(top,brickCoordinates)] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                        }
                        else {
                            // Determining brick's corners.
                            double brickCornerX = -brickHalfwidth + brickCoordinates[i][0];
                            double brickCornerY = brickHalfheight + brickCoordinates[i][1];
                            // Finding the portion which will be reflected.
                            double portion = portionFinder(newBallPos,velocityOnX,velocityOnY,
                                    ballRadius,brickCornerX,brickCornerY);
                            // Preventing ball to go in the brick during collision by subtracting the distance having gone inside.
                            newBallPos[0] -= (portion * velocityOnX);
                            newBallPos[1] -= (portion * velocityOnY);
                            // Calculating new velocities after a collision.
                            phi = phiFinder(newBallPos,brickCoordinates[i],brickHalfheight,brickHalfwidth);
                            newVelocityOnX = ballVelocity * Math.cos(Math.PI + 2 * phi - theta);
                            newVelocityOnY = ballVelocity * Math.sin(Math.PI + 2 * phi - theta);
                            velocityOnX = newVelocityOnX;
                            velocityOnY = newVelocityOnY;
                            // Adding subtracted portion to collided ball.
                            newBallPos[0] += (portion * velocityOnX);
                            newBallPos[1] += (portion * velocityOnY);
                            // Marking the brick as hit and adding score for one brick to score of the player.
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                        }
                    }

                    // Top right corner
                    else if ((Math.sqrt(Math.pow(rightDistanceOnX,2) + Math.pow(topDistanceOnY,2)) <= ballRadius)
                            && (newBallPos[0] >= brickCoordinates[i][0] + brickHalfwidth)
                            && (newBallPos[1] >= brickCoordinates[i][1] + brickHalfheight)
                            && (newBallPos[0] <= brickCoordinates[i][0] + brickHalfwidth + ballRadius)
                            && (newBallPos[1] <= brickCoordinates[i][1] + brickHalfheight+ ballRadius)){
                        // If there is a block in right side during corner collision, the collision occurs as a surface collision.
                        if(isRight){
                            velocityOnY = -velocityOnY;
                            brickHitStatus[i] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                            brickHitStatus[indexOf(right,brickCoordinates)] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                        }
                        // If there is a block in top side during corner collision, the collision occurs as a surface collision.
                        else if(isTop){
                            velocityOnX = -velocityOnX;
                            brickHitStatus[i] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                            brickHitStatus[indexOf(top,brickCoordinates)] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                        }
                        else {
                            // Determining brick's corners.
                            double brickCornerX = brickHalfwidth + brickCoordinates[i][0];
                            double brickCornerY = brickHalfheight + brickCoordinates[i][1];
                            // Finding the portion which will be reflected.
                            double portion = portionFinder(newBallPos,velocityOnX,velocityOnY,
                                    ballRadius,brickCornerX,brickCornerY);
                            // Preventing ball to go in the brick during collision by subtracting the distance having gone inside.
                            newBallPos[0] -= (portion * velocityOnX);
                            newBallPos[1] -= (portion * velocityOnY);
                            // Calculating new velocities after a collision.
                            phi = phiFinder(newBallPos,brickCoordinates[i],brickHalfheight,brickHalfwidth);
                            newVelocityOnX = ballVelocity * Math.cos(Math.PI + 2 * phi - theta);
                            newVelocityOnY = ballVelocity * Math.sin(Math.PI + 2 * phi - theta);
                            velocityOnX = newVelocityOnX;
                            velocityOnY = newVelocityOnY;
                            // Adding subtracted portion to collided ball.
                            newBallPos[0] += (portion * velocityOnX);
                            newBallPos[1] += (portion * velocityOnY);
                            // Marking the brick as hit and adding score for one brick to score of the player.
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                        }
                    }
                    // Bottom left corner
                    else if ((Math.sqrt(Math.pow(leftDistanceOnX,2) + Math.pow(bottomDistanceOnY,2)) <= ballRadius)
                            && (newBallPos[0] <= brickCoordinates[i][0] - brickHalfwidth)
                            && (newBallPos[1] <= brickCoordinates[i][1] - brickHalfheight)
                            && (newBallPos[0] >= brickCoordinates[i][0] - brickHalfwidth - ballRadius)
                            && (newBallPos[1] >= brickCoordinates[i][1] - brickHalfheight - ballRadius)){
                        // If there is a block in left side during corner collision, the collision occurs as a surface collision.
                        if(isLeft){
                            velocityOnY = -velocityOnY;
                            brickHitStatus[i] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                            brickHitStatus[indexOf(left,brickCoordinates)] = true;
                            scoreOfPlayer +=scoreForOneBrick;
                        }
                        // If there is a block in bottom side during corner collision, the collision occurs as a surface collision.
                        else if(isBottom){
                            velocityOnX = -velocityOnX;
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                            brickHitStatus[indexOf(bottom,brickCoordinates)] = true;
                            scoreOfPlayer += scoreForOneBrick;
                        }
                        else {
                            // Determining brick's corners.
                            double brickCornerX = -brickHalfwidth + brickCoordinates[i][0];
                            double brickCornerY = -brickHalfheight + brickCoordinates[i][1];
                            // Finding the portion which will be reflected.
                            double portion = portionFinder(newBallPos,velocityOnX,velocityOnY,
                                    ballRadius,brickCornerX,brickCornerY);
                            newBallPos[0] -= (portion * velocityOnX);
                            newBallPos[1] -= (portion * velocityOnY);
                            // Preventing ball to go in the brick during collision by subtracting the distance having gone inside.
                            phi = phiFinder(newBallPos,brickCoordinates[i],brickHalfheight,brickHalfwidth);
                            newVelocityOnX = ballVelocity * Math.cos(Math.PI + 2 * phi - theta);
                            newVelocityOnY = ballVelocity * Math.sin(Math.PI + 2 * phi - theta);
                            velocityOnX = newVelocityOnX;
                            velocityOnY = newVelocityOnY;
                            // Adding subtracted portion to collided ball.
                            newBallPos[0] += (portion * velocityOnX);
                            newBallPos[1] += (portion * velocityOnY);
                            // Marking the brick as hit and adding score for one brick to score of the player.
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                        }
                    }

                    // Bottom right corner
                    else if ((Math.sqrt(Math.pow(rightDistanceOnX,2) + Math.pow(bottomDistanceOnY,2)) <= ballRadius)
                            && (newBallPos[0] >= brickCoordinates[i][0] + brickHalfwidth)
                            && (newBallPos[1] <= brickCoordinates[i][1] - brickHalfheight)
                            && (newBallPos[0] <= brickCoordinates[i][0] + brickHalfwidth + ballRadius)
                            && (newBallPos[1] >= brickCoordinates[i][1] - brickHalfheight - ballRadius)){
                        // If there is a block in right side during corner collision, the collision occurs as a surface collision.
                        if(isRight){
                            velocityOnY = -velocityOnY;
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                            brickHitStatus[indexOf(right,brickCoordinates)] = true;
                            scoreOfPlayer += scoreForOneBrick;
                        }
                        // If there is a block in bottom side during corner collision, the collision occurs as a surface collision.
                        else if(isBottom){
                            velocityOnX = -velocityOnX;
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                            brickHitStatus[indexOf(bottom,brickCoordinates)] = true;
                            scoreOfPlayer += scoreForOneBrick;
                        }
                        else {
                            // Determining brick's corners.
                            double brickCornerX = brickHalfwidth + brickCoordinates[i][0];
                            double brickCornerY = -brickHalfheight + brickCoordinates[i][1];
                            // Finding the portion which will be reflected.
                            double portion = portionFinder(newBallPos,velocityOnX,velocityOnY,
                                    ballRadius,brickCornerX,brickCornerY);
                            // Preventing ball to go in the brick during collision by subtracting the distance having gone inside.
                            newBallPos[0] -= (portion * velocityOnX);
                            newBallPos[1] -= (portion * velocityOnY);
                            phi = phiFinder(newBallPos,brickCoordinates[i],brickHalfheight,brickHalfwidth);
                            newVelocityOnX = ballVelocity * Math.cos(Math.PI + 2 * phi - theta);
                            newVelocityOnY = ballVelocity * Math.sin(Math.PI + 2 * phi - theta);
                            velocityOnX = newVelocityOnX;
                            velocityOnY = newVelocityOnY;
                            // Adding subtracted portion to collided ball.
                            newBallPos[0] += (portion * velocityOnX);
                            newBallPos[1] += (portion * velocityOnY);
                            // Marking the brick as hit and adding score for one brick to score of the player.
                            brickHitStatus[i] = true;
                            scoreOfPlayer += scoreForOneBrick;
                        }

                    }
                    // Top and Bottom
                    if (((Math.abs(newBallPos[0] - brickCoordinates[i][0] ) < brickHalfwidth)
                            &&(newBallPos[1] < brickCoordinates[i][1] + brickHalfheight + ballRadius &&
                            newBallPos[1] > brickCoordinates[i][1] - brickHalfheight - ballRadius))) {
                        // Finding the distance reflected for the top surface collisions.
                        if (newBallPos[1] > brickCoordinates[i][1]){
                            // Fixing the corner collision bug causing from order of execution.
                            double distanceX = newBallPos[0] - (brickCoordinates[i][0] + brickHalfwidth);
                            double distanceY = newBallPos[1] - (brickCoordinates[i][1] + brickHalfheight);
                            if(cornerWillCollide(distanceX,distanceY,ballRadius,isRight)){
                                brickHitStatus[indexOf(right,brickCoordinates)] = true;
                                scoreOfPlayer += scoreForOneBrick;
                            }
                            newBallPos[1] += distanceFinder((brickCoordinates[i][1] + brickHalfheight),
                                    newBallPos[1],"bottom",ballRadius);
                        }
                        // Finding the distance reflected for the top surface collisions.
                        else {
                            // Fixing the corner collision bug causing from order of execution.
                            double distanceX = newBallPos[0] - (brickCoordinates[i][0] + brickHalfwidth);
                            double distanceY = newBallPos[1] - (brickCoordinates[i][1] - brickHalfheight);
                            if(cornerWillCollide(distanceX,distanceY,ballRadius,isRight)){
                                brickHitStatus[indexOf(right,brickCoordinates)] = true;
                                scoreOfPlayer += scoreForOneBrick;
                            }
                            newBallPos[1] += distanceFinder((brickCoordinates[i][1] - brickHalfheight),
                                    newBallPos[1],"top",ballRadius);
                        }
                        // Calculating new velocities
                        velocityOnY = - velocityOnY;
                        // Marking the brick as hit and adding score for one brick to score of the player.
                        brickHitStatus[i] = true;
                        scoreOfPlayer += scoreForOneBrick;
                    }

                    // Right and Left
                    else if (((Math.abs(newBallPos[1] - brickCoordinates[i][1]) < brickHalfheight)
                            &&(newBallPos[0]  < brickCoordinates[i][0] + brickHalfwidth + ballRadius &&
                            newBallPos[0]  > brickCoordinates[i][0] - brickHalfwidth - ballRadius))) {
                        // Finding the distance reflected for the right surface collisions.
                        if (newBallPos[0] > brickCoordinates[i][0]){
                            // Fixing the corner collision bug causing from order of execution.
                            double distanceX = newBallPos[0] - (brickCoordinates[i][0] + brickHalfwidth);
                            double distanceY = newBallPos[1] - (brickCoordinates[i][1] - brickHalfheight);
                            if(cornerWillCollide(distanceX,distanceY,ballRadius,isBottom)){
                                brickHitStatus[indexOf(right,brickCoordinates)] = true;
                                scoreOfPlayer += scoreForOneBrick;
                            }
                            newBallPos[0] += distanceFinder((brickCoordinates[i][0] + brickHalfwidth),
                                    newBallPos[0],"left",ballRadius);
                        }
                        // Finding the distance reflected for the left surface collisions.
                        else {
                            // Fixing the corner collision bug causing from order of execution.
                            double distanceX = newBallPos[0] - (brickCoordinates[i][0] - brickHalfwidth);
                            double distanceY = newBallPos[1] - (brickCoordinates[i][1] - brickHalfheight);
                            if(cornerWillCollide(distanceX,distanceY,ballRadius,isBottom)){
                                brickHitStatus[indexOf(right,brickCoordinates)] = true;
                                scoreOfPlayer += scoreForOneBrick;
                            }
                            newBallPos[0] += distanceFinder((brickCoordinates[i][0] - brickHalfwidth),
                                    newBallPos[0],"right",ballRadius);
                        }
                        // Calculating new velocities
                        velocityOnX = -velocityOnX;
                        // Marking the brick as hit and adding score for one brick to score of the player.
                        brickHitStatus[i] = true;
                        scoreOfPlayer += scoreForOneBrick;
                    }
                }

                // Game over
                if (newBallPos[1] < ballRadius) {
                    isGameOver = true;
                }
                // Victory
                if (isAllHit(brickHitStatus)) {
                    isVictory = true;
                }

                // Placing ball to the canvas
                StdDraw.setPenColor(ballColor);
                StdDraw.filledCircle(newBallPos[0],newBallPos[1],ballRadius);

                // Placing paddle to the canvas
                StdDraw.setPenColor(paddleColor);
                StdDraw.filledRectangle(paddlePos[0],paddlePos[1],paddleHalfwidth,paddleHalfheight);

                // Placing bricks to the canvas
                for(int i = 0 ; i < numOfBricks ; i++){
                    if(!brickHitStatus[i]){
                        StdDraw.setPenColor(brickColors[i]);
                        StdDraw.filledRectangle(brickCoordinates[i][0], brickCoordinates[i][1],brickHalfwidth,brickHalfheight);
                    }
                }

                // Writing score
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.textLeft(680,380, "Score: " + scoreOfPlayer);

                StdDraw.show();
            }

        }

        StdDraw.clear();

        if (isGameOver){
            // Placing bricks to the canvas
            for(int i = 0 ; i < numOfBricks ; i++){
                if(!brickHitStatus[i]) {
                    StdDraw.setPenColor(brickColors[i]);
                    StdDraw.filledRectangle(brickCoordinates[i][0], brickCoordinates[i][1], brickHalfwidth, brickHalfheight);
                }
            }
            // Placing ball to the canvas
            StdDraw.setPenColor(ballColor);
            StdDraw.filledCircle(newBallPos[0],newBallPos[1],ballRadius);

            // Placing paddle to the canvas
            StdDraw.setPenColor(paddleColor);
            StdDraw.filledRectangle(paddlePos[0],paddlePos[1],paddleHalfwidth,paddleHalfheight);

            // Placing game over text to the canvas
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Helvetica",Font.BOLD, 40));
            StdDraw.text(400,100, "GAME OVER !");

            // Placing score to the canvas
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Helvetica",Font.PLAIN, 20));
            StdDraw.text(400,70, "Score: " + scoreOfPlayer);

            StdDraw.show();
        }

        if (isVictory){

            // Placing ball to the canvas
            StdDraw.setPenColor(ballColor);
            StdDraw.filledCircle(newBallPos[0],newBallPos[1],ballRadius);

            // Placing paddle to the canvas
            StdDraw.setPenColor(paddleColor);
            StdDraw.filledRectangle(paddlePos[0],paddlePos[1],paddleHalfwidth,paddleHalfheight);

            // Placing victory text to the canvas
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Helvetica",Font.BOLD, 40));
            StdDraw.text(400,100, "VICTORY !");

            // Placing score text to the canvas
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new Font("Helvetica",Font.PLAIN, 20));
            StdDraw.text(400,70, "Score: " + scoreOfPlayer);

            StdDraw.show();
        }

    }
    /* contains method of array lists adapted to arrays.
     * @param coordinate: The coordinate to check.
     * @param arr: The array of coordinates.
     * @param hit: Boolean array indicating which coordinates have been hit.
     * @return true or false.
     */
    static boolean contains(double[] coordinate, double[][] arr , boolean[] hit){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0] == coordinate[0] && arr[i][1] == coordinate[1] && !hit[i] )
                return true;
        }
        return false;

    }

    /* indexOf method of array lists adapted to arrays.
     * @param coordinate: The coordinate to search for.
     * @param arr: The array of coordinates.
     * @return The index of the coordinate, or -1 if not found.
     */
    static int indexOf(double[] coordinate, double[][] arr){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0] == coordinate[0] && arr[i][1] == coordinate[1])
                return i;
        }
        return -1;
    }

    /* A method to detect if all bricks are hit
     * @param arr: Boolean array indicating brick hit status.
     * @return True if all bricks are hit, otherwise false.
     */
    static boolean isAllHit(boolean[] arr) {
        for (boolean bool : arr) {
            if (!bool) {
                return false;
            }
        }
        return true;
    }

    /* A method to find the angle between the x-axis and velocity vector (theta).
     * @param velocityOnX: X component of velocity.
     * @param velocityOnY: Y component of velocity.
     * @return The angle in radians.
     */
    static double thetaFinder(double velocityOnX,double velocityOnY){
        double theta;
        if(velocityOnX > 0) {
            theta = Math.atan(velocityOnY / velocityOnX);
        }
        else {
            theta = Math.atan(velocityOnY / velocityOnX) + Math.PI;
        }
        return theta;
    }

    /* A method to find the angle between the x-axis and normal vector (phi).
     * @param newBallPos: Current position of the ball.
     * @param pos: Position of the object (brick/paddle).
     * @param halfheight: Half of the object's height.
     *@param halfwidth: Half of the object's width.
     * @return The angle in radians.
     */
    static double phiFinder(double[] newBallPos,double[] pos,double halfheight,double halfwidth){
        double phi;

        // Top right corner
        if (newBallPos[0] >= pos[0] && newBallPos[1] >= pos[1]){
            phi= Math.atan((newBallPos[1] -(pos[1]+halfheight))/(newBallPos[0] -(pos[0]+halfwidth)));
        }
        // Top left corner
        else if (newBallPos[0] <= pos[0] && newBallPos[1] >= pos[1]){
            phi= Math.atan((newBallPos[1] -(pos[1]+halfheight))/(newBallPos[0]  - (pos[0]-halfwidth))) + Math.PI;
        }
        // Bottom right corner
        else if (newBallPos[0] >= pos[0] && newBallPos[1] <= pos[1]){
            phi= Math.atan((newBallPos[1] - (pos[1]-halfheight))/(newBallPos[0] - (pos[0]+halfwidth)));
        }
        // Bottom left corner
        // Else condition is (newBallPos[0] <= pos[0] && newBallPos[1] <= pos[1])
        else {
            phi= Math.atan((newBallPos[1] -(pos[1]-halfheight))/(newBallPos[0] - (pos[0]-halfwidth))) + Math.PI;
        }
        return phi;
    }
    /* A method to find the portion reflected.
     * @param ballPos: Current ball position.
     * @param velocityX: X component of velocity.
     * @param velocityY: Y component of velocity.
     * @param ballRadius: Radius of the ball.
     * @param brickCornerX: X coordinate of the brick corner.
     * @param brickCornerY: Y coordinate of the brick corner.
     * @return The portion reflected.
     */
    static double portionFinder(double[] ballPos,double velocityX,double velocityY,double ballRadius,
                                double brickCornerX, double brickCornerY){

        double posX= ballPos[0];
        double posY= ballPos[1];
        // Deriving a discriminant formula for the prtion calculation.
        double a = Math.pow(velocityX,2) + Math.pow(velocityY,2);
        double b = 2 * (brickCornerX * velocityX + brickCornerY * velocityY
                - posX * velocityX - posY * velocityY);
        double c = Math.pow(posX,2) + Math.pow(posY,2)
                + Math.pow(brickCornerX,2) + Math.pow(brickCornerY,2)
                - 2 * posX * brickCornerX - 2 * posY * brickCornerY
                - Math.pow(ballRadius,2);
        double delta = Math.pow(b,2) - 4 * a * c;
        double rootOne = (- b - Math.sqrt(delta)) / (2 * a);
        double rootTwo = (- b + Math.sqrt(delta)) / (2 * a);

        // Selecting the correct root.
        if ( 0 < rootOne && rootOne < 1){
            return rootOne;
        }
        if ( 0 < rootTwo && rootTwo < 1){
            return rootTwo;
        }
        // Else statement does not occur, the program forced to write it.
        else{
            return -1;
        }
    }
    /* A method to find the distance reflected.
     * @param surface: Position of the collision surface.
     * @param ballPos: Current ball position.
     * @param direction: The direction of collision ("top", "bottom", "left", "right").
     * @param radius: Radius of the ball.
     * @return The reflected distance.
     */

    static double distanceFinder(double surface, double ballPos, String direction, double radius){
        double distance;
        if (direction.equals("top") || direction.equals("right")) {
            distance = 2 * (surface - (ballPos + radius));
        }
        else if (direction.equals("bottom") || direction.equals("left")) {
            distance = 2 * (surface - (ballPos - radius));
        }
        // Else statement does not occur, the program forced to write it.
        else {
            distance = -1;
        }
        return distance;
    }
    /* Determines if a corner collision will occur in a case of bug.
     * @param distanceOnX: Distance between the ball and the object's X coordinate.
     * @param distanceOnY: Distance between the ball and the object's Y coordinate.
     * @param ballRadius: Radius of the ball.
     * @param isDirection: Boolean indicating movement towards the object.
     * @return True if a collision will occur, otherwise false.
     */
    static boolean cornerWillCollide(double distanceOnX, double distanceOnY, double ballRadius, boolean isDirection){
        double euclideanDistance = Math.sqrt(Math.pow(distanceOnX,2) + Math.pow(distanceOnY,2));
        return (euclideanDistance <= ballRadius) && isDirection;
    }

}

