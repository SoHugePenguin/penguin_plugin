package Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Windows.Build_Item_Win.FillType;

import cn.nukkit.Player;
import cn.nukkit.block.Block;

import java.util.ArrayList;

import static Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Main_PluginBase.undo_map;

public class UndoFill {
    public static void undo_Fill(Player player) {
        ArrayList<Block> blocks = undo_map.get(player.getName());

        if (blocks.size() == 0) {
            player.sendToast("Warning", "目前只能撤回上一次的操作！");
            return;
        }

        for (Block block : blocks) {
            player.getLevel().setBlock(block, block);
        }

        undo_map.put(player.getName(), new ArrayList<>());
    }
}
