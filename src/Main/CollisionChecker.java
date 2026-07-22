package Main;

import Entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                break;
        }

        if (entityLeftCol >= 0 && entityRightCol < gp.maxWorldCol && entityTopRow >= 0 && entityBottomRow < gp.maxWorldRow) {
            switch (entity.direction) {
                case "up":
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    break;
                case "down":
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    break;
                case "left":
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    break;
                case "right":
                    tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    break;
                default:
                    return;
            }

            if ((tileNum1 >= 0 && tileNum1 < gp.tileM.tile.length && gp.tileM.tile[tileNum1] != null && gp.tileM.tile[tileNum1].collision)
                    || (tileNum2 >= 0 && tileNum2 < gp.tileM.tile.length && gp.tileM.tile[tileNum2] != null && gp.tileM.tile[tileNum2].collision)) {
                entity.collisionOn = true;
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = -1;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                        }
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i; // Return the index of the object collided with
                            }
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i; // Return the index of the object collided with
                            }
                        }
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i; // Return the index of the object collided with
                            }
                        }
                        break;
                }

                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i; // Return the index of the object collided with
                    }
                }

                // Reset solid area positions
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }

        return index; // Return -1 if no collision with any object
    }

}
