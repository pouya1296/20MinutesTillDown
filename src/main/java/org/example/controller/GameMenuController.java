package org.example.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.example.Model.*;
import org.example.Model.enums.Enemy;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GameMenuController {
    public static boolean movementHandle(float delta, Game game, int speed, Map<String, Integer> keys) {
        if (Gdx.input.isKeyPressed(keys.get("UP")) && Gdx.input.isKeyPressed(keys.get("LEFT"))) {
            game.x -= speed * delta;
            game.y += speed * delta;
            return true;
        } else if (Gdx.input.isKeyPressed(keys.get("UP")) && Gdx.input.isKeyPressed(keys.get("RIGHT"))) {
            game.x += speed * delta;
            game.y += speed * delta;
            return true;
        } else if (Gdx.input.isKeyPressed(keys.get("DOWN")) && Gdx.input.isKeyPressed(keys.get("LEFT"))) {
            game.x -= speed * delta;
            game.y -= speed * delta;
            return true;
        } else if (Gdx.input.isKeyPressed(keys.get("DOWN")) && Gdx.input.isKeyPressed(keys.get("RIGHT"))) {
            game.x += speed * delta;
            game.y -= speed * delta;
            return true;
        } else {
            if (Gdx.input.isKeyPressed(keys.get("UP"))) {
                game.y += speed * delta;
                return true;
            }
            if (Gdx.input.isKeyPressed(keys.get("DOWN"))) {
                game.y -= speed * delta;
                return true;
            }
            if (Gdx.input.isKeyPressed(keys.get("LEFT"))) {
                game.x -= speed * delta;
                return true;
            }
            if (Gdx.input.isKeyPressed(keys.get("RIGHT"))) {
                game.x += speed * delta;
                return true;
            }
        }
        return false;
    }

    public static float playerTreeHandle(float playerSize, float saveX, float saveY, Game game, float invincibleTimer) {
        for (Tree tree : game.trees) {
            if (game.x + playerSize / 2 > tree.x && game.x < tree.x + 64 &&
                    game.y + playerSize > tree.y && game.y < tree.y + 70) {
                game.x = saveX;
                game.y = saveY;
                System.out.println(invincibleTimer);
                if (invincibleTimer == 0f) {
                    game.hp -= 1;
                    return 1f;
                }
            }
        }
        return invincibleTimer;
    }

    public static void bulletHandle(float delta, Game game, List<Bullet> bullets, Texture treeTexture, Animation<TextureRegion> tentacleWalkAnimation, Animation<TextureRegion> eyebatAnimation) {
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(delta, game);
            boolean hit = false;
            for (Tree tree : game.trees) {
                if (bullet.x + 8 > tree.x && bullet.x < tree.x + treeTexture.getWidth() &&
                        bullet.y + 8 > tree.y && bullet.y < tree.y + treeTexture.getHeight()) {
                    bullet.active = false;
                    break;
                }
            }
            if (!hit) {
                Iterator<org.example.Model.Enemy> enemyIt = game.enemies.iterator();
                while (enemyIt.hasNext()) {
                    org.example.Model.Enemy enemy = enemyIt.next();
                    if (enemy instanceof TentacleMonster) {
                        if (bullet.x + 4 > enemy.x && bullet.x < enemy.x + tentacleWalkAnimation.getKeyFrame(enemy.stateTime).getRegionWidth() &&
                                bullet.y + 4 > enemy.y && bullet.y < enemy.y + tentacleWalkAnimation.getKeyFrame(enemy.stateTime).getRegionHeight()) {

                            enemy.hp -= game.weapon.damage;
                            hit = true;
                            if (enemy.hp <= 0) {
                                game.drops.add(new Drop(enemy.x, enemy.y, enemy.dropPath));
                                game.kills++;
                                enemyIt.remove();
                            }
                            break;
                        }
                    } else if (enemy instanceof EyeBat) {
                        if (bullet.x + 4 > enemy.x && bullet.x < enemy.x + eyebatAnimation.getKeyFrame(enemy.stateTime).getRegionWidth() &&
                                bullet.y + 4 > enemy.y && bullet.y < enemy.y + eyebatAnimation.getKeyFrame(enemy.stateTime).getRegionHeight()) {

                            enemy.hp -= game.weapon.damage;
                            hit = true;
                            if (enemy.hp <= 0) {
                                game.drops.add(new Drop(enemy.x, enemy.y, enemy.dropPath));
                                game.kills++;
                                enemyIt.remove();
                            }
                            break;
                        }
                    } else if (enemy instanceof Elder) {
                        if (bullet.x + 4 > enemy.x && bullet.x < enemy.x + 100 &&
                                bullet.y + 4 > enemy.y && bullet.y < enemy.y + 100) {

                            enemy.hp -= game.weapon.damage;
                            hit = true;
                            if (enemy.hp <= 0) {
                                game.drops.add(new Drop(enemy.x, enemy.y, enemy.dropPath));
                                game.kills++;
                                game.shieldActive = false;
                                enemyIt.remove();
                            }
                            break;
                        }
                    }

                }
            }
            if (hit || !bullet.active) {
                bulletIterator.remove();
            }
        }
    }

    public static void dropHandle(Game game, Texture idlePlayerTexture) {
        Iterator<Drop> dropIt = game.drops.iterator();
        Rectangle playerRect = new Rectangle(game.x, game.y, idlePlayerTexture.getWidth(), idlePlayerTexture.getHeight());
        while (dropIt.hasNext()) {
            Drop drop = dropIt.next();
            if (!drop.collected && drop.bounds.overlaps(playerRect)) {
                drop.collected = true;
                game.kills++;
                game.xp += Drop.XP_VALUE;
                dropIt.remove();
            }
        }
    }

    public static void eyeBatShoot(float delta, Game game, List<Bullet> eyebatBullets) {
        Iterator<org.example.Model.Enemy> enemy = game.enemies.iterator();
        while (enemy.hasNext()) {
            org.example.Model.Enemy enemy1 = enemy.next();
            if (enemy1 instanceof EyeBat) {
                ((EyeBat) enemy1).shootTimer += delta;
                if (((EyeBat) enemy1).shootTimer >= 3f) {
                    ((EyeBat) enemy1).shootTimer -= 3f;

                    eyebatBullets.add(new Bullet(
                            enemy1.x + 2, enemy1.y + 2,
                            game.x + 16, game.y + 16
                    ));
                }
            }
        }

        Iterator<Bullet> ebbIt = eyebatBullets.iterator();
        while (ebbIt.hasNext()) {
            Bullet b = ebbIt.next();
            b.update(delta, game);

            if (b.x > game.x && b.x < game.x + 32 && b.y > game.y && b.y < game.y + 32) {
                if (game.invincibleTimer == 0f)
                    game.hp -= 1;

                ebbIt.remove();
            } else if (!b.active) {
                ebbIt.remove();
            }
        }
    }

    public static void tentacleSpawn(Game game) {
        if (game.enemySpawnTimer >= 3.0f) {
            game.enemySpawnTimer -= 3.0f;

            int spawnCount = (int) (game.time / 30f);

            for (int s = 0; s < spawnCount; s++) {
                int edge = (int) (Math.random() * 4);
                float sx = 0, sy = 0;
                if (edge == 0) {
                    sx = (float) Math.random() * Gdx.graphics.getWidth();
                    sy = Gdx.graphics.getHeight() + 32;
                } else if (edge == 1) {
                    sx = (float) Math.random() * Gdx.graphics.getWidth();
                    sy = -32;
                } else if (edge == 2) {
                    sx = -32;
                    sy = (float) Math.random() * Gdx.graphics.getHeight();
                } else {
                    sx = Gdx.graphics.getWidth() + 32;
                    sy = (float) Math.random() * Gdx.graphics.getHeight();
                }
                float wx = sx + game.x - Gdx.graphics.getWidth() / 2;
                float wy = sy + game.y - Gdx.graphics.getHeight() / 2;
                game.enemies.add(new TentacleMonster(
                        wx, wy,
                        org.example.Model.enums.Enemy.TENTACLE.texturePaths,
                        Enemy.TENTACLE.hp));
            }
        }
    }

    public static float eyebatSpawn(float delta, Game game, float eyebatSpawnTimer) {
        if (game.time < game.totalTime / 4f)
            return 0f;

        if (eyebatSpawnTimer + delta >= 10f) {
            int count = Math.max(0, (int) (4 * game.time - game.totalTime + 30) / 60);
            for (int k = 0; k < count; k++) {
                int edge = (int) (Math.random() * 4);
                float sx = 0, sy = 0;
                if (edge == 0) {
                    sx = (float) Math.random() * Gdx.graphics.getWidth();
                    sy = Gdx.graphics.getHeight() + 32;
                } else if (edge == 1) {
                    sx = (float) Math.random() * Gdx.graphics.getWidth();
                    sy = -32;
                } else if (edge == 2) {
                    sx = -32;
                    sy = (float) Math.random() * Gdx.graphics.getHeight();
                } else {
                    sx = Gdx.graphics.getWidth() + 32;
                    sy = (float) Math.random() * Gdx.graphics.getHeight();
                }
                float wx = sx + game.x - Gdx.graphics.getWidth() / 2;
                float wy = sy + game.y - Gdx.graphics.getHeight() / 2;
                game.enemies.add(new EyeBat(
                        wx, wy,
                        Enemy.EYE_BAT.texturePaths,
                        Enemy.EYE_BAT.hp
                ));
            }
            return eyebatSpawnTimer + delta - 10f;
        }
        return eyebatSpawnTimer + delta;
    }

    public static void playerEnemy(Game game, Animation<TextureRegion> tentacleWalkAnimation, Animation<TextureRegion> eyebatAnimation) {
        Iterator<org.example.Model.Enemy> enemyIt = game.enemies.iterator();
        while (enemyIt.hasNext()) {
            org.example.Model.Enemy enemy = enemyIt.next();
            if (enemy instanceof TentacleMonster) {
                if (game.x + 7 > enemy.x && game.x < enemy.x + tentacleWalkAnimation.getKeyFrame(enemy.stateTime).getRegionWidth() &&
                        game.y + 7 > enemy.y && game.y < enemy.y + tentacleWalkAnimation.getKeyFrame(enemy.stateTime).getRegionHeight()) {
                    if (game.invincibleTimer == 0f) {
                        game.invincibleTimer = 1f;
                        game.hp--;
                    }
                    break;
                }
            } else if (enemy instanceof EyeBat) {
                if (game.x + 7 > enemy.x && game.x < enemy.x + eyebatAnimation.getKeyFrame(enemy.stateTime).getRegionWidth() &&
                        game.y + 7 > enemy.y && game.y < enemy.y + eyebatAnimation.getKeyFrame(enemy.stateTime).getRegionHeight()) {
                    if (game.invincibleTimer == 0f) {
                        game.invincibleTimer = 1f;
                        game.hp--;
                    }
                    break;
                }
            } else if (enemy instanceof Elder) {
                if (game.x + 7 > enemy.x && game.x < enemy.x + 100 &&
                        game.y + 7 > enemy.y && game.y < enemy.y + 100) {
                    if (game.invincibleTimer == 0f) {
                        game.invincibleTimer = 1f;
                        game.hp--;
                    }
                    break;
                }
            }
        }

    }

    public static void elderSpawn(Game game) {
        if (game.time < game.totalTime / 2f)
            return;
        if (game.elderSpawn)
            return;
        game.elderSpawn = true;
        game.shieldActive = true;

        float sx = 0;
        float sy = 0;
        float wx = sx + game.x - Gdx.graphics.getWidth() / 4;
        float wy = sy + game.y - Gdx.graphics.getHeight() / 4;
        game.enemies.add(new Elder(
                wx, wy,
                Enemy.ELDER.texturePaths,
                Enemy.ELDER.hp));
    }

    public static void playerShield(Game game, float saveX, float saveY) {
        if (game.shieldActive) {
            if (game.x - 5 < Gdx.graphics.getWidth() / 2 - game.shieldHalfWidth) {
                game.x = saveX + 6;
                game.y = saveY;
                game.hp--;
            }
            if (game.x + 10 > Gdx.graphics.getWidth() / 2 + game.shieldHalfWidth) {
                game.x = saveX - 6;
                game.y = saveY;
                game.hp--;
            }
            if (game.y - 5 < Gdx.graphics.getHeight() / 2 - game.shieldHalfHeight) {
                game.x = saveX;
                game.y = saveY + 6;
                game.hp--;
            }
            if (game.y + 15 > Gdx.graphics.getHeight() / 2 + game.shieldHalfHeight) {
                game.x = saveX;
                game.y = saveY - 6;
                game.hp--;
            }
        }
    }

    public static void shieldHandle(Game game, float delta, Camera camera) {
        if (game.shieldActive) {
            game.shieldHalfWidth = Math.max(0, game.shieldHalfWidth - game.shieldShrinkSpeed * delta);
            game.shieldHalfHeight = Math.max(0, game.shieldHalfHeight - game.shieldShrinkSpeed * delta);


            Iterator<org.example.Model.Enemy> it = game.enemies.iterator();
            while (it.hasNext()) {
                org.example.Model.Enemy enemy = it.next();


                if (enemy.x < Gdx.graphics.getWidth() / 2 - game.shieldHalfWidth
                        || enemy.x > Gdx.graphics.getWidth() / 2 + game.shieldHalfWidth
                        || enemy.y < Gdx.graphics.getHeight() / 2 - game.shieldHalfHeight
                        || enemy.y > Gdx.graphics.getHeight() / 2 + game.shieldHalfHeight) {
                    if (enemy instanceof Elder)
                        game.shieldActive = false;
                    game.drops.add(new Drop(enemy.x, enemy.y, enemy.dropPath));
                    it.remove();
                }
            }

        }
    }
}
