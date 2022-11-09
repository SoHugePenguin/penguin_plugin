package Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin;

import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Blocks.*;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Entity.Anchor;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Entity.BaseNpc;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Entity.Car1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.boot_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.chest_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.helmet_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Armors.legging_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Custom_Pickaxe;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Edibles.Fire_Pepper_Item;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Saber.spear_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Saber.sword_1;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Saber.sword_2;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Summon_egg.anchor_egg;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Summon_egg.anchor_information;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Tools.BuildGod_Item;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Items.Tools.TheWorld_Menu_Item;
import Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Windows.Build_Item_Win.Coordinate_sorting;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.block.Block;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.defaults.SayCommand;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.provider.CustomClassEntityProvider;
import cn.nukkit.event.Listener;
import cn.nukkit.item.Item;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.level.particle.RedstoneParticle;
import cn.nukkit.math.Vector3;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.Config;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

import static Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Tool.ball_tool.ball;
import static Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Tool.round_tool.round;
import static Minecraft.nukkit.cn.sohugepenguin.plugin.testPlugin.Windows.Build_Item_Win.Build_Menu.getBuildWindow;

public class Main_PluginBase extends PluginBase implements Listener {
    public static String date_show;  //时间显示
    public static ArrayList<Player> online_players = new ArrayList<>(); //在线玩家列表

    public static Map<String, ArrayList<Vector3>> build_map = new HashMap<>();

    public static Map<String, ArrayList<Block>> undo_map = new HashMap<>();
    static Player Plugin_player;
    ArrayList<Vector3> v_collect = new ArrayList<>();
    static ArrayList<Player> access_p = new ArrayList<>();
    Config player_join;

    public static void access(Player player) {
        access_p = new ArrayList<>();
        access_p.add(player);
        Plugin_player = player;
    }

    private void LoadWorld() {
        File[] listFiles = new File("worlds").listFiles();
        //导入新世界(非家园)
        assert listFiles != null;
        for (File wFolder : listFiles) {
            if (wFolder.isDirectory() && !getServer().isLevelLoaded(wFolder.getName())) {
                if (!wFolder.getName().contains("的家园")) {
                    getServer().loadLevel(wFolder.getName());
                }
            }
        }
    }

    public void onLoad() {
//        注释文本abc
        PluginLogger log = this.getLogger();
        if (Item.fromString("np:world_menu").equals(Item.get(0))) {
            try {
                Entity.registerCustomEntity(new CustomClassEntityProvider(BaseNpc.def, BaseNpc.class));
                Entity.registerCustomEntity(new CustomClassEntityProvider(Car1.def, Car1.class));
                Entity.registerCustomEntity(new CustomClassEntityProvider(Anchor.def , Anchor.class));
                Item.registerCustomItem(List.of(Custom_Pickaxe.class , TheWorld_Menu_Item.class,
                        Fire_Pepper_Item.class , BuildGod_Item.class , sword_1.class , sword_2.class , spear_1.class ,
                        anchor_egg.class , helmet_1.class , legging_1.class , boot_1.class , chest_1.class
                ));
                Block.registerCustomBlock(List.of(test_slab.class , block1.class, block2.class, block3.class, block4.class,
                        block5.class, block6.class, block7.class, block8.class, block9.class, block10.class,
                        block11.class, block12.class, block13.class, block14.class, block15.class, block16.class,
                        block17.class, block18.class, block19.class, block20.class, block21.class, block22.class,
                        block23.class, block24.class, block25.class, block26.class, block27.class, block28.class,
                        block29.class, block30.class, block31.class, block32.class, block33.class, block34.class,
                        block35.class, block36.class, block37.class, block38.class, block39.class, block40.class));
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                     InvocationTargetException e) {
                log.info("§c实验性玩法插件启动失败!");
                e.printStackTrace();
            }
        } else {
            Entity.registerCustomEntity(new CustomClassEntityProvider(BaseNpc.def, BaseNpc.class));
            Entity.registerCustomEntity(new CustomClassEntityProvider(Car1.def, Car1.class));
            Entity.registerCustomEntity(new CustomClassEntityProvider(Anchor.def , Anchor.class));
            log.info("§b插件新增物已加载，避免Warn已经取消方块的载入");

            Map<UUID, Player> online_list = Server.getInstance().getOnlinePlayers();
            online_players = new ArrayList<>();
            if (online_list.size() > 0) {
                Iterator<UUID> keyIterator = online_list.keySet().iterator();
                for (int i = 0; i < online_list.size(); i++) {
                    Object key = keyIterator.next();
                    online_list.get(key).sendToast("\uE103§aReload>>>服务器插件已重启！", "§g  遇到BUG请及时汇报鹅先生");
                }
            }
        }
    }

    public void onEnable() {
//        ZlibThreadLocal zlibThreadLocal = new ZlibThreadLocal();

        File[] listFiles = new File("worlds").listFiles();
        //导入新世界
        assert listFiles != null;
        for (File wFolder : listFiles) {
            if (wFolder.isDirectory() && !getServer().isLevelLoaded(wFolder.getName())) {
                if (!wFolder.getName().contains("的家园")) {
                    getServer().loadLevel(wFolder.getName());
                }
            }
        }
        File BaseFile = new File("penguin_plugin");
        if (!BaseFile.exists()) {
            BaseFile.mkdirs();
            getLogger().warning("§c缺失penguin_plugin文件夹，已为您自动下载！");
        }
        File file = new File("penguin_plugin", "SkyBlock_World");
        if (!file.exists()) {
            file.mkdirs();
            getLogger().warning("§c缺失SkyBlock_World文件夹，已为您自动下载！");
        }
        File file2 = new File("penguin_plugin", "Builder_Save");
        if (!file2.exists()) {
            file2.mkdirs();
            getLogger().warning("§c缺失Builder_Save文件夹，已为您自动下载！");
        }
        File file3 = new File("penguin_plugin", "skins");
        if (!file3.exists()) {
            file3.mkdirs();
            getLogger().warning("§c缺失skins文件夹，已为您自动下载！");
        }

        Path world_data = Paths.get(Server.getInstance().getDataPath() + "penguin_plugin\\SkyBlock_World\\SkyBlock_Home.zip");
        if (!Files.exists(world_data)) {
            getLogger().warning("§c缺失SkyBlock_Home.zip，已为您自动下载！");
            try {
                Files.copy(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/SkyBlock_Home.zip")), world_data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path re_pack = Paths.get(Server.getInstance().getDataPath() + "resource_packs\\Penguin_RE_pnx.zip");
        if (!Files.exists(re_pack)) {
            getLogger().warning("§c缺失Penguin_RE_pnx.zip资源文件，已为您自动下载！请重新启动服务器！");
            try {
                Files.copy(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/Penguin_RE_pnx.zip")), re_pack);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Path steve_png = Paths.get(Server.getInstance().getDataPath() + "penguin_plugin\\skins\\steve.png");
        if (!Files.exists(steve_png)) {
            try {
                Files.copy(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("resources/steve.png")), steve_png);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.getServer().getPluginManager().registerEvents(new ServerListener(), this);
        this.getLogger().info("§m§6古之立大事者，不惟有超世之才，亦必有坚忍不拔之志。");
        this.getDataFolder().mkdirs();
        this.saveDefaultConfig();
        Config player = new Config(this.getDataFolder() + "/player.yml", 2);
        this.saveResource("player.yml");

        Config npc_config = new Config("penguin_plugin/Npc_config/npcBase.yml", 2);
        if (npc_config.get("npcNumber") == null) {
            npc_config.set("npcNumber", 0);
            npc_config.set("name", "steve");
            npc_config.set("skin", "steve.png");
            npc_config.set("model_size", 1.0f);
            npc_config.set("eye_height", 1.8f);
        }
        npc_config.save();


        this.getServer().getScheduler().scheduleRepeatingTask(this, () -> {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            date_show = date.toString();
            int data_test = 0;
            for (int i = 0; i < date_show.length(); i++) {
                if (String.valueOf(date_show.charAt(i)).equals(" ")) {
                    data_test++;
                }
                if (data_test == 3) {
                    date_show = "at " + date_show.substring(i + 1, i + 9);
                }
            }
//            定时获取在线玩家列表
            Map<UUID, Player> online_list = Server.getInstance().getOnlinePlayers();
             online_players = new ArrayList<>();
            if (online_list.size() > 0) {
                Iterator<UUID> keyIterator = online_list.keySet().iterator();
                for (int i = 0; i < online_list.size(); i++) {
                    Object key = keyIterator.next();
                    online_players.add(online_list.get(key));
                }
            }
//            reload后一键添加空对象（创世神）
            if(build_map.size() ==0){
                for (Player online_player : online_players) {
                    build_map.put(online_player.getName(),new ArrayList<>());
                }
            }

            if(undo_map.size() ==0){
                for (Player online_player : online_players) {
                   undo_map.put(online_player.getName(),new ArrayList<>());
                }
            }


            if (online_players.size() > 0) {
                String TargetBlock, Biomes;
                int ID, ID_DATA;
                for (Player p : online_players) {
                    if (p.getTargetBlock(5) == null) {
                        TargetBlock = "Air";
                        ID = 0;
                        ID_DATA = 0;
                    } else {
                        TargetBlock = p.getTargetBlock(5).getName();
                        ID = p.getTargetBlock(5).getBlockId();
                        ID_DATA = p.getTargetBlock(5).getExactIntStorage();
                    }

                    byte[] BiomesList = Objects.requireNonNull(p.getChunk()).getBiomeIdArray();
                    int list_idr = (int) (p.getX() - p.getChunkX() * 16) * 16 + (int) (p.getZ() - p.getChunkZ() * 16);
                    if (BiomesList[list_idr] < 0) {
                        Biomes = Biome.getBiome(p.getLevel().getBiomeId(p.getChunkX(), p.getChunkZ())).getName();
                    } else {
                        Biomes = Biome.getBiome(BiomesList[list_idr]).getName();
                    }
                    p.sendActionBar("§6群系：" + Biomes + " §aPing：" + p.getPing() + "ms"
                            + "\n§c方块：" + TargetBlock + " (" + ID + ":" + ID_DATA + ")"
                            + "\n§b手持：" + p.getInventory().getItemInHand().getName() + " ID：" + p.getInventory().getItemInHand().getId());
                }
            }

            long i = player.getLong("all");
            player_join = player;
            if (Plugin_player == null) {
                if (player.getLong("all") == 0L) {
                    player.set("all", 1);
                    player.set("isFirst", false);
                    player.save();
                }
            } else {
                boolean test = false;

                for (int j = 0; (long) j < i; ++j) {
                    if (!Plugin_player.getName().equals(player.getString(String.valueOf(j)))) {
                        if (!player.getBoolean("isFirst")) {
                            player.set(String.valueOf(j), Plugin_player.getName());
                            player.set("isFirst", true);
                            player.save();
                            test = true;
                        }
                    } else {
                        test = true;
                    }

                    if (!test && (long) j == i - 1L) {
                        player.set(String.valueOf(j + 1), Plugin_player.getName());
                        player.set("all", j + 2);
                        player.save();
                    }
                }
            }

            //创世神可视化粒子//
            Player WoodAxePlayer;
            ArrayList<Vector3> v;
            v_collect = new ArrayList<>();
            for (Player online_player : online_players) {
                WoodAxePlayer = online_player;
                v = build_map.get(online_player.getName());
                if (v != null && v.size() == 2 && v.get(0) != null && v.get(1) != null) {
                    Coordinate_sorting.sorting(v);
                    for (double k1 = v.get(0).x; k1 < v.get(1).x + 2; k1++) {
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(0).y, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(1).y + 1, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(0).y, v.get(1).z + 1)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(k1, v.get(1).y + 1, v.get(1).z + 1)));
                    }
                    for (double k2 = v.get(0).y; k2 < v.get(1).y + 2; k2++) {
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, k2, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, k2, v.get(0).z)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, k2, v.get(1).z + 1)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, k2, v.get(1).z + 1)));
                    }
                    for (double k3 = v.get(0).z; k3 < v.get(1).z + 2; k3++) {
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, v.get(0).y, k3)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(0).x, v.get(1).y + 1, k3)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, v.get(0).y, k3)));
                        WoodAxePlayer.getLevel().addParticle(new RedstoneParticle(new Vector3(v.get(1).x + 1, v.get(1).y + 1, k3)));
                    }
                }
            }

//            mapTest();

        }, 10);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (command.getName().equals("get")) {
            boolean NewPlayer = true;
            if (args.length == 1 && args[0].equals("1")) {
                HashSet<String> achievements = p.achievements;
                String[] NewList = achievements.toArray(new String[0]);
                int var10 = NewList.length;

                int j;
                for (j = 0; j < var10; ++j) {
                    String s = NewList[j];
                    if (s.equals("我出生辣！")) {
                        p.sendMessage("§c§m<Server>您已经领取过新人礼包了,莫贪！");
                        NewPlayer = false;
                        break;
                    }
                }

                if (NewPlayer) {
                    p.achievements.add("我出生辣！");
                    SayCommand.broadcastCommandMessage(p, "§a领取了新人礼包");
                    StringBuilder item_all_name = new StringBuilder(" ");
                    int[] item_id = new int[]{46, 1, 267, 1, 298, 1, 299, 1, 300, 1, 301, 1, 322, 3, 426, 1};

                    for (j = 0; j < item_id.length; j += 2) {
                        Item item = Item.fromString(String.valueOf(item_id[j]));
                        item.setCount(item_id[j + 1]);
                        p.getInventory().addItem(item.clone());
                        item_all_name.insert(0, "§b§m" + item.getName() + "x" + item.getCount() + "\n");
                    }

                    p.sendMessage("§d§m" + date_show + "\n§6§m" + p.getName() + ", 您获得了：");
                    p.sendMessage(String.valueOf(item_all_name));
                }
            } else {
                sender.sendMessage("§m§6欢迎来到企鹅的服务器！\n§b输入/get 1获取新人大礼包奥！\nQQ群:793186488");
            }
        }

        if (command.getName().equals("do")) {
            p.showFormWindow(getBuildWindow());
        }

        if (command.getName().equals("biomes")){
            //player.onChunkChanged(FullChunk c)  SuperIce666推荐，发送区块更新到玩家；
            int x = p.getChunkX(),z=p.getChunkZ();
            if(args.length==1 && !Objects.equals(args[0], "list")) {
                byte id = Byte.parseByte(args[0]);
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        Objects.requireNonNull(p.getChunk()).setBiome(i, j, Biome.getBiome(id));
                        p.getChunk().setBiomeId(i, j, id);
                    }
                }
//                    p.getServer().getLevel(p.getLevel().getId()).generateChunk(x,z);  //重载区块
                p.onChunkChanged(p.getChunk());
            } else if (args.length==1 && args[0].equals("list")) {
                p.sendMessage(Arrays.toString(Objects.requireNonNull(p.getChunk()).getBiomeIdArray()));
                StringBuilder BiomesList = new StringBuilder();
                for (int i = 0; i < Biome.unorderedBiomes.size(); i++) {
                    BiomesList.append("§6[").append(i).append("]§a").append(Biome.getBiome(i).getName());
                }
                p.sendMessage("§b群系列表：\n" + BiomesList);
                p.sendMessage("你所在块的群系为： " + Biome.getBiome(p.getLevel().getBiomeId(x, z)).getName());
            }else p.sendMessage("§a/Biomes list 列出已有全部群系类型ID\n§b/Biomes [ID] 将你所在的区块改为对应id类型的群系");
        }

        if (command.getName().equals("round")) {
            if(args.length==1){
                p.sendMessage("你好像没有写要填充什么方块吗？仔细看看哦");
            }else if(args.length>1){
                p.sendMessage("§c严禁使用下落方块，掉落物，液体！否则立马删除OP!不管你是谁！");
                round(p,args);
            }else p.sendMessage("画圆指令用法：/round 半径(整数，必选) 方块ID(必选) 方块特殊值（可选）keep(保持，可选，使用时，方块特殊值必须要写)");
        }

        if (command.getName().equals("ball")) {
            if(args.length==1){
                p.sendMessage("你好像没有写要填充什么方块吗？仔细看看哦");
            }else if(args.length>1){
                p.sendMessage("§c严禁使用下落方块，掉落物，液体！否则立马删除OP!不管你是谁！");
                SayCommand.broadcastCommandMessage(sender, "§a" + p.getName() + "使用了" + Arrays.toString(args));
                ball(p,args);
            }else p.sendMessage("画球指令用法：/ball 半径(整数，必选) 方块ID(必选) 方块特殊值（可选）keep(保持，可选，使用时，方块特殊值必须要写)");
        }

        if (command.getName().equals("zoom") && args.length == 2) {
            p = this.getServer().getPlayer(args[0]);
            float a = Float.parseFloat(args[1]);
            if ((double)a >= 0.5 && a <= 3.0F) {
                p.setScale(a);
                sender.sendMessage("§a§mSuccess!");
                p.sendMessage("§b§m你的身体已被缩放！");
            } else {
                sender.sendMessage(date_show + "§m§c<Error>数值只能在0.5~3之间！");
            }
        }

        if (command.getName().equals("clear")) {
            if (args.length == 0) {
                p.getInventory().clearAll();
                p.sendMessage("§b背包已清空！");
            } else if (args.length == 1) {
                p = this.getServer().getPlayer(args[0]);
                if (p == sender) {
                    p.getInventory().clearAll();
                    sender.sendMessage("§a§m Success");
                    p.sendMessage("§b背包已清空！");
                } else if (p == null) {
                    sender.sendMessage("§m§c<Error>未查找到该名字对应的在线玩家！");
                } else {
                    p.getInventory().clearAll();
                    sender.sendMessage("§a§m Success");
                    p.sendMessage("§b你的背包已被" + sender.getName() + " 清空！");
                }
            } else {
                sender.sendMessage("§d§m/clear  清理自己背包\n/clear [name] 清理指定玩家背包");
            }
        }

        if (command.getName().equals("ping")) {
            p = (Player)sender;
            String device;
            if (args.length == 0) {
                if(p.getLoginChainData().getDeviceOS() ==7){
                     device = "PC端";
                }else {
                     device ="移动端";
                }
                p.sendMessage("§b§mPort: " + p.getPort() + "§a\nPing: " + p.getPing() + "§6ms\n你正在 " +
                       device + " 上玩我的世界！");
            } else {
                p.sendMessage("§m/ping");
            }

            Car1 anchor =  new Car1(p.getLocation().getChunk(), Entity.getDefaultNBT(p.getPosition())
                    .putString("account","null")
                    .putCompound("Skin", (new CompoundTag()))
            );
            anchor.spawnToAll();
            p.sendMessage(String.valueOf(anchor_information.name));
            p.sendMessage(String.valueOf(anchor_information.vector3));
        }

        if (command.getName().equals("achievement")) {
            p = (Player)sender;
            if (args.length == 0) {
                p.sendMessage("§g§m你的成就列表:\n" + ((Player)sender).achievements);
            } else if (args.length == 2 && args[1].equals("clear")) {
                p = this.getServer().getPlayer(args[0]);
                if (p == sender) {
                    p.achievements.clear();
                    p.sendMessage("§b成就列表已清空！");
                } else if (p == null) {
                    sender.sendMessage("§m§c" + date_show + "<Error>未查找到该名字对应的在线玩家！");
                } else {
                    p.achievements.clear();
                    sender.sendMessage("§a§m Success");
                    p.sendMessage("§b你的成就列表已被 " + sender.getName() + " 清空！");
                }
            } else if (args.length == 3 && args[1].equals("add")) {
                p = this.getServer().getPlayer(args[0]);
                if (p == sender) {
                    p.achievements.add(args[2]);
                    p.sendMessage("§b成就 " + args[2] + " 已加载！");
                } else if (p == null) {
                    sender.sendMessage("§m§c" + date_show + "<Error>未查找到该名字对应的在线玩家！");
                } else {
                    p.achievements.add(args[2]);
                    sender.sendMessage("操作成功！");
                    p.sendMessage("§b" + sender.getName() + "为你加载了 " + args[2] + " 成就");
                }
            } else if (args.length == 3 && args[1].equals("remove")) {
                p = this.getServer().getPlayer(args[0]);
                if (p == sender) {
                    p.achievements.remove(args[2]);
                    p.sendMessage("§b成就 " + args[2] + " 已删除！");
                } else if (p == null) {
                    sender.sendMessage("§m§c" + date_show + "<Error>未查找到该名字对应的在线玩家！");
                } else {
                    p.achievements.remove(args[2]);
                    sender.sendMessage("操作成功！");
                    p.sendMessage("§b" + sender.getName() + "为你删除了 " + args[2] + " 成就");
                }
            } else {
                sender.sendMessage("§d§m/achievement                   输出自己的成就列表\n/achievement [玩家名] clear          清除所有成就\n/achievement [玩家名] add [成就]     增加成就(数据)\n/achievement [玩家名] remove [成就]  删除成就(数据)\n§a----------------------");
            }
        }

        if (command.getName().equals("set")) {
            sender.sendMessage("现已改为/do");
        }

        return true;
    }
}
