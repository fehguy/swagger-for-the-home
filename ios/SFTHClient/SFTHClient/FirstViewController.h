//
//  FirstViewController.h
//  SFTHClient
//
//  Created by Tony Tam on 11/1/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SWGZwaveApi.h"

@interface FirstViewController : UIViewController {
    UISwitch* switch1;
    UISwitch* switch2;
    UISwitch* switch3;
    UISwitch* switch4;
}

-(IBAction)switch1ValueChanged:(id)sender;
-(IBAction)switch1Timer:(id)sender;

-(IBAction)switch2ValueChanged:(id)sender;
-(IBAction)switch2Timer:(id)sender;

-(IBAction)switch3ValueChanged:(id)sender;
-(IBAction)switch3Timer:(id)sender;

-(IBAction)switch4ValueChanged:(id)sender;
-(IBAction)switch4Timer:(id)sender;

@property (nonatomic, retain) IBOutlet UISwitch* switch1;
@property (nonatomic, retain) IBOutlet UISwitch* switch2;
@property (nonatomic, retain) IBOutlet UISwitch* switch3;
@property (nonatomic, retain) IBOutlet UISwitch* switch4;

-(void) refreshState;

@end

