//
//  ThirdViewController.h
//  SFTHClient
//
//  Created by Tony Tam on 11/16/14.
//  Copyright (c) 2014 Eatbacon. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SWGEnvironmentAPi.h"

@interface ThirdViewController : UIViewController {
    bool connected;

    UIButton* logoButton;

    UILabel* label1;
    UILabel* label2;
    UILabel* label3;
    UILabel* label4;
    UILabel* label5;
    UILabel* label6;
}

-(IBAction)refreshState;

@property (nonatomic, retain) IBOutlet UIButton* logoButton;

@property (nonatomic, retain) IBOutlet UILabel* label1;
@property (nonatomic, retain) IBOutlet UILabel* label2;
@property (nonatomic, retain) IBOutlet UILabel* label3;
@property (nonatomic, retain) IBOutlet UILabel* label4;
@property (nonatomic, retain) IBOutlet UILabel* label5;
@property (nonatomic, retain) IBOutlet UILabel* label6;

@end
