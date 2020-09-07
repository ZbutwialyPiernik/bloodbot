package com.zbutwialypiernik.bloodbot.service.killboard;

import com.zbutwialypiernik.bloodbot.config.Config;
import com.zbutwialypiernik.bloodbot.entity.albion.AlbionEquipment;
import com.zbutwialypiernik.bloodbot.repository.IconRepository;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Log4j2
public class KillImageGenerator {

    public static final String OUTPUT_EXTENSION = "png";

    public static class Vector2 {
        public int x;
        public int y;

        public Vector2(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final IconRepository iconRepository;

    private Image backgroundImage;

    private Config.KillboardImageGeneratorConfig config;

    public KillImageGenerator(Config.KillboardImageGeneratorConfig config, IconRepository iconRepository) {
        this.iconRepository = iconRepository;
        this.config = config;

        try {
            this.backgroundImage = ImageIO.read(new File(config.getBackgroundPath()));
        } catch (IOException e) {
            log.error("Problem with loading background", e);
        }
    }

    public byte[] generate(AlbionEquipment killerEquipment, AlbionEquipment victimEquipment) {
        BufferedImage image = new BufferedImage(backgroundImage.getWidth(null), backgroundImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();

        g2d.drawImage(backgroundImage, 0, 0, null);

        try {
            drawInventory(killerEquipment, g2d, new Vector2(0, 0));

            drawInventory(victimEquipment, g2d, new Vector2(backgroundImage.getWidth(null) / 2, 0));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            g2d.dispose();
        }

        return toByteArray(image);
    }

    private void drawInventory(AlbionEquipment equipment, Graphics2D g2d, Vector2 offset) throws IOException {
        if (equipment.getBag() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getBag().getType(), config.getIconSize()))),
                    config.getBagPosition().x + offset.x, config.getBagPosition().y + offset.y, null);

        if (equipment.getMainHand() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getMainHand().getType(), config.getIconSize()))),
                    config.getMainHandPosition().x + offset.x, config.getMainHandPosition().y + offset.y, null);

        if (equipment.getHead() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getHead().getType(), config.getIconSize()))),
                    config.getHeadPosition().x + offset.x, config.getHeadPosition().y + offset.y, null);

        if (equipment.getArmor() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getArmor().getType(), config.getIconSize()))),
                    config.getShoesPosition().x + offset.x, config.getArmorPosition().y + offset.y, null);

        if (equipment.getShoes() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getShoes().getType(), config.getIconSize()))),
                    config.getShoesPosition().x + offset.x, config.getShoesPosition().y + offset.y, null);

        if (equipment.getCape() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getCape().getType(), config.getIconSize()))),
                    config.getCapePosition().x + offset.x, config.getCapePosition().y + offset.y, null);

        if (equipment.getOffHand() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getOffHand().getType(), config.getIconSize()))),
                    config.getOffhandPosition().x + offset.x, config.getOffhandPosition().y + offset.y, null);

        if (equipment.getFood() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getFood().getType(), config.getIconSize()))),
                    config.getFoodPosition().x + offset.x, config.getFoodPosition().y + offset.y, null);

        if (equipment.getMount() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getMount().getType(), config.getIconSize()))),
                    config.getMountPosition().x + offset.x, config.getMountPosition().y + offset.y, null);

        if (equipment.getPotion() != null)
            g2d.drawImage(ImageIO.read(new ByteArrayInputStream(iconRepository.getItemIcon(equipment.getPotion().getType(), config.getIconSize()))),
                    config.getPotionPosition().x + offset.x, config.getPotionPosition().y + offset.y, null);
    }

    private byte[] toByteArray(BufferedImage image) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, OUTPUT_EXTENSION, outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            log.error(e);
        }

        return null;
    }

}
